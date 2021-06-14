package com.nomadapps.watchlist.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nomadapps.watchlist.model.FavoriteMovieEntity

const val DB_VERSION = 1

// entity
@Database(entities = [FavoriteMovieEntity::class], version = DB_VERSION, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var INSTANCE: LocalDatabase? = null
        fun getAppDatabase(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, LocalDatabase::class.java, "LocalDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}