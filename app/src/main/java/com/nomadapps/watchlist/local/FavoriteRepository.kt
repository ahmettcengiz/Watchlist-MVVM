package com.nomadapps.watchlist.local

import androidx.lifecycle.LiveData
import com.nomadapps.watchlist.model.FavoriteMovieEntity

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val allFavoriteDetail: LiveData<List<FavoriteMovieEntity>> = favoriteDao.findAll()

    fun insertFavoriteMovieDetail(favoriteMovieEntity: FavoriteMovieEntity) {
        favoriteDao.insert(favoriteMovieEntity)
    }

    fun deleteFavoriteMovieDetail(favoriteMovieEntity: FavoriteMovieEntity) {
        favoriteDao.delete(favoriteMovieEntity)
    }
}