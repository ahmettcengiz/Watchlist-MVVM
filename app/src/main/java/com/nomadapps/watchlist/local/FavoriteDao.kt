package com.nomadapps.watchlist.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nomadapps.watchlist.model.FavoriteMovieEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteMovie ORDER BY ID ASC")
    fun findAll(): LiveData<List<FavoriteMovieEntity>>

    @Delete
    fun delete(favoriteMovieEntity: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteMovieEntity: FavoriteMovieEntity)
}