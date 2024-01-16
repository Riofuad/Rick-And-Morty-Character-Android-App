package com.takehomechallenge.iman.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.takehomechallenge.iman.ui.adapter.CharacterAdapter
import com.takehomechallenge.iman.ui.viewmodels.MainViewModel
import com.takehomechallenge.iman.R
import com.takehomechallenge.iman.data.remote.response.CharacterList
import com.takehomechallenge.iman.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        const val TAG = "MainActivity"
        const val INITIAL_CHARACTER = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_name)

        mainViewModel.character.observe(this) { character ->
            setCharacterData(character)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.noResult.observe(this) {
            showNoResult(it)
        }

        mainViewModel.snackBarText.observe(this) {
            Snackbar.make(
                window.decorView.rootView,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvCharacter.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.findCharacter(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    mainViewModel.findCharacter("")
                }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                val favoriteIntent = Intent(this, FavoriteCharacterActivity::class.java)
                startActivity(favoriteIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCharacterData(character: List<CharacterList>) {
        val listCharacter = ArrayList<CharacterList>()
        for (i in character) {
            listCharacter.clear()
            listCharacter.addAll(character)
        }
        val adapter = CharacterAdapter(listCharacter)
        binding.rvCharacter.adapter = adapter

        adapter.setOnItemClickCallback(object : CharacterAdapter.OnItemClickCallback {
            override fun onItemClicked(data: CharacterList) {
                moveToDetail(data)
            }
        })
    }

    private fun moveToDetail(data: CharacterList) {
        val detailCharacterIntent = Intent(this@MainActivity, DetailCharacterActivity::class.java)
        detailCharacterIntent.putExtra(DetailCharacterActivity.EXTRA_ID_CHARACTER, data.id)
        detailCharacterIntent.putExtra(DetailCharacterActivity.EXTRA_NAME_CHARACTER, data.name)
        startActivity(detailCharacterIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNoResult(noResult: Boolean) {
        if (noResult) {
            binding.tvNoResult.visibility = View.VISIBLE
            binding.rvCharacter.visibility = View.GONE
        } else {
            binding.tvNoResult.visibility = View.GONE
            binding.rvCharacter.visibility = View.VISIBLE
        }
    }
}