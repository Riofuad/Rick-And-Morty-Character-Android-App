package com.takehomechallenge.iman.ui.viewmodels

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.iman.data.FavoriteCharacterRepository
import com.takehomechallenge.iman.data.local.entity.FavoriteCharacter
import com.takehomechallenge.iman.data.remote.network.ApiConfig
import com.takehomechallenge.iman.data.remote.response.CharacterDetailResponse
import com.takehomechallenge.iman.di.EspressoIdling
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailViewModel(application: Application) : ViewModel() {
    private val mCharacterRepository: FavoriteCharacterRepository =
        FavoriteCharacterRepository(application)

    private val _detail = MutableLiveData<CharacterDetailResponse>()
    val detail: LiveData<CharacterDetailResponse> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> = _snackBarText

    fun getFavoriteUser(characterId: Int): LiveData<FavoriteCharacter> =
        mCharacterRepository.getFavoriteCharacter(characterId)

    fun insert(character: FavoriteCharacter) {
        mCharacterRepository.insert(character)
        Log.d("FavoriteAddViewModel", "${character.name}; ${character.image} added")
    }

    fun delete(id: Int) {
        mCharacterRepository.delete(id)
    }

    internal fun getDetail(id: Int) {
        _isLoading.value = true
        EspressoIdling.increment()

        val client = ApiConfig.getApiService().getCharacter(id)
        client.enqueue(object : Callback<CharacterDetailResponse> {
            override fun onResponse(
                call: Call<CharacterDetailResponse>,
                response: Response<CharacterDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detail.value = response.body()
                        if (!EspressoIdling.idlingResource.isIdleNow) {
                            EspressoIdling.decrement()
                        }
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    _snackBarText.value = "An error has occurred!"
                }
            }

            override fun onFailure(call: Call<CharacterDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _snackBarText.value = "An error has occurred! Please try again later."
            }
        })
    }
}