package com.takehomechallenge.iman.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.takehomechallenge.iman.data.local.entity.FavoriteCharacter

@Dao
interface FavoriteCharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteCharater: FavoriteCharacter)

    @Query("SELECT * FROM favoriteCharacter ORDER BY id ASC")
    fun getCharacter(): LiveData<List<FavoriteCharacter>>

    @Query("SELECT * FROM favoriteCharacter WHERE id = :favoriteCharacter")
    fun getFavoriteCharacter(favoriteCharacter: Int): LiveData<FavoriteCharacter>

    @Query("DELETE FROM favoriteCharacter WHERE id = :favoriteCharacter")
    fun deleteFavoriteCharacter(favoriteCharacter: Int)
}