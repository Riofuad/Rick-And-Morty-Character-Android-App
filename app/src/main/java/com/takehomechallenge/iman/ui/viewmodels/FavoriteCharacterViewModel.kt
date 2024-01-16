package com.takehomechallenge.iman.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.iman.data.FavoriteCharacterRepository
import com.takehomechallenge.iman.data.local.entity.FavoriteCharacter

class FavoriteCharacterViewModel(application: Application) : ViewModel() {
    private val mCharacterRepository: FavoriteCharacterRepository =
        FavoriteCharacterRepository(application)

    fun getCharacter(): LiveData<List<FavoriteCharacter>> = mCharacterRepository.getCharacter()
}