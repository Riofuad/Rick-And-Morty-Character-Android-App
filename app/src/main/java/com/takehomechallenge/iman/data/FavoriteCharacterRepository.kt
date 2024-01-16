package com.takehomechallenge.iman.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.takehomechallenge.iman.data.local.entity.FavoriteCharacter
import com.takehomechallenge.iman.data.local.room.FavoriteCharacterDao
import com.takehomechallenge.iman.data.local.room.FavoriteCharacterRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteCharacterRepository(application: Application) {
    private val mCharacterDao: FavoriteCharacterDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteCharacterRoomDatabase.getInstance(application)
        mCharacterDao = db.favoriteCharacterDao()
    }

    fun getCharacter(): LiveData<List<FavoriteCharacter>> = mCharacterDao.getCharacter()

    fun getFavoriteCharacter(id: Int): LiveData<FavoriteCharacter> =
        mCharacterDao.getFavoriteCharacter(id)

    fun insert(favoriteUser: FavoriteCharacter) {
        executorService.execute { mCharacterDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: Int) {
        executorService.execute { mCharacterDao.deleteFavoriteCharacter(favoriteUser) }
    }
}