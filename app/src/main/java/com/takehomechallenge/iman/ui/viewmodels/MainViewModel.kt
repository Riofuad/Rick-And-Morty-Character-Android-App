package com.takehomechallenge.iman.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.iman.data.remote.network.ApiConfig
import com.takehomechallenge.iman.data.remote.response.CharacterList
import com.takehomechallenge.iman.data.remote.response.CharacterListResponse
import com.takehomechallenge.iman.ui.activities.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _character = MutableLiveData<List<CharacterList>>()
    val character: LiveData<List<CharacterList>> = _character

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> = _snackBarText

    private val _noResult = MutableLiveData<Boolean>()
    val noResult: LiveData<Boolean> = _noResult

    init {
        findCharacter(MainActivity.INITIAL_CHARACTER)
    }

    internal fun findCharacter(query: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchCharacter(query)
        client.enqueue(object : Callback<CharacterListResponse> {
            override fun onResponse(
                call: Call<CharacterListResponse>,
                response: Response<CharacterListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _noResult.value = false
                        _character.value = response.body()?.results
                    }
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                    _noResult.value = true
                    _snackBarText.value = "An error has occurred!"
                }
            }

            override fun onFailure(call: Call<CharacterListResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure: ${t.message}")
                _snackBarText.value = "An error has occurred! please try again later"
            }
        })
    }
}