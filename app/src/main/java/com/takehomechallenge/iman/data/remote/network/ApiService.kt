package com.takehomechallenge.iman.data.remote.network

import com.takehomechallenge.iman.data.remote.response.CharacterDetailResponse
import com.takehomechallenge.iman.data.remote.response.CharacterListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    fun searchCharacter(@Query("name") name: String?): Call<CharacterListResponse>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Int): Call<CharacterDetailResponse>
}