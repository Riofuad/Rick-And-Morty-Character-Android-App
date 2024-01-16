package com.takehomechallenge.iman.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.takehomechallenge.iman.data.local.entity.FavoriteCharacter

@Database(entities = [FavoriteCharacter::class], version = 1)
abstract class FavoriteCharacterRoomDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteCharacterRoomDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): FavoriteCharacterRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteCharacterRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteCharacterRoomDatabase::class.java,
                        "FavoriteCharacter"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoriteCharacterRoomDatabase
        }
    }
}