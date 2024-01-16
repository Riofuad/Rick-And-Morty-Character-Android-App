package com.takehomechallenge.iman.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterListResponse(

    @field:SerializedName("results")
    val results: List<CharacterList>,

    @field:SerializedName("info")
    val info: Info
) : Parcelable

@Parcelize
data class Origin(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("url")
    val url: String
) : Parcelable

@Parcelize
data class Location(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("url")
    val url: String
) : Parcelable

@Parcelize
data class Info(

    @field:SerializedName("next")
    val next: String?,

    @field:SerializedName("pages")
    val pages: Int,

    @field:SerializedName("prev")
    val prev: String?,

    @field:SerializedName("count")
    val count: Int
) : Parcelable

@Parcelize
data class CharacterList(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("species")
    val species: String,

    @field:SerializedName("created")
    val created: String,

    @field:SerializedName("origin")
    val origin: Origin,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("location")
    val location: Location,

    @field:SerializedName("episode")
    val episode: List<String>,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("status")
    val status: String
) : Parcelable
