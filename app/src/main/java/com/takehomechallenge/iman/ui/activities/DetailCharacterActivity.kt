package com.takehomechallenge.iman.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.takehomechallenge.iman.ui.viewmodels.CharacterDetailViewModel
import com.takehomechallenge.iman.R
import com.takehomechallenge.iman.ui.viewmodels.ViewModelFactory
import com.takehomechallenge.iman.data.local.entity.FavoriteCharacter
import com.takehomechallenge.iman.data.remote.response.CharacterDetailResponse
import com.takehomechallenge.iman.databinding.ActivityDetailCharacterBinding

class DetailCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCharacterBinding
    private lateinit var actionBar: ActionBar
    private val detailViewModel by viewModels<CharacterDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = intent.getStringExtra(EXTRA_NAME_CHARACTER).toString()
        actionBar.setDisplayHomeAsUpEnabled(true)

        val characterId = intent.getIntExtra(EXTRA_ID_CHARACTER, 0)
        detailViewModel.getDetail(characterId)

        detailViewModel.detail.observe(this) { characterDetail ->
            setUpCharacterData(characterDetail)
            setUpFavoriteCharacter(characterDetail)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.snackBarText.observe(this) {
            Snackbar.make(
                window.decorView.rootView,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setUpCharacterData(character: CharacterDetailResponse) {
        binding.apply {
            Glide.with(this@DetailCharacterActivity)
                .load(character.image)
                .into(ivDetailPhoto)
            tvDetailName.text = character.name
            tvSpeciesDetail.text = character.species
            tvGenderDetail.text = character.gender
            tvOriginDetail.text = character.origin.name
            tvLocationDetail.text = character.location.name
        }
    }

    private fun setUpFavoriteCharacter(characterDetail: CharacterDetailResponse) {
        detailViewModel.getFavoriteUser(characterDetail.id).observe(this) {
            val favoriteCharacter =
                FavoriteCharacter(characterDetail.id, characterDetail.name, characterDetail.image)
            var isFavorite = false

            if (it != null) {
                isFavorite = true
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_full)
            }

            binding.fabFavorite.setOnClickListener {
                if (!isFavorite) {
                    detailViewModel.insert(favoriteCharacter)
                    isFavorite = true
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_full)
                } else {
                    detailViewModel.delete(favoriteCharacter.id)
                    isFavorite = false
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    companion object {
        const val EXTRA_ID_CHARACTER = "extra_id_chracter"
        const val EXTRA_NAME_CHARACTER = "extra_name_chracter"
    }
}