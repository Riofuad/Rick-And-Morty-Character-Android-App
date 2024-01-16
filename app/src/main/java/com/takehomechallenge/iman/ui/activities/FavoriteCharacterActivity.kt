package com.takehomechallenge.iman.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.takehomechallenge.iman.R
import com.takehomechallenge.iman.data.local.entity.FavoriteCharacter
import com.takehomechallenge.iman.ui.adapter.FavoriteCharacterAdapter
import com.takehomechallenge.iman.ui.viewmodels.FavoriteCharacterViewModel
import com.takehomechallenge.iman.databinding.ActivityFavoriteCharacterBinding
import com.takehomechallenge.iman.ui.viewmodels.ViewModelFactory

class FavoriteCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteCharacterBinding
    private lateinit var adapter: FavoriteCharacterAdapter
    private val favoriteUserViewModel by viewModels<FavoriteCharacterViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_character)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteUserViewModel.getCharacter().observe(this) { listFavoriteCharacter ->
            if (listFavoriteCharacter != null) {
                binding.tvNoFavoriteCharacter.visibility = View.GONE
                adapter.setListFavorites(listFavoriteCharacter)
            }

            if (listFavoriteCharacter.isEmpty()) {
                binding.tvNoFavoriteCharacter.visibility = View.VISIBLE
            }
        }

        adapter = FavoriteCharacterAdapter()
        binding.rvFavoriteCharacter.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteCharacter.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteCharacterAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteCharacter) {
                moveToDetail(data)
            }
        })
    }

    private fun moveToDetail(data: FavoriteCharacter) {
        val detailCharacterIntent =
            Intent(this@FavoriteCharacterActivity, DetailCharacterActivity::class.java)
        detailCharacterIntent.putExtra(DetailCharacterActivity.EXTRA_ID_CHARACTER, data.id)
        detailCharacterIntent.putExtra(DetailCharacterActivity.EXTRA_NAME_CHARACTER, data.name)
        startActivity(detailCharacterIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}