package com.takehomechallenge.iman.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class FavoriteCharacter(
    @field:PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "id")
    var id: Int = 0,

    @field:ColumnInfo(name = "name")
    var name: String? = null,

    @field:ColumnInfo(name = "image")
    var image: String? = null
) : Parcelable