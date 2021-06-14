package com.nomadapps.watchlist.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nomadapps.watchlist.model.FavoriteMovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    val allFavoriteMovieDetail: LiveData<List<FavoriteMovieEntity>>

    private val repository: FavoriteRepository

    init {
        val favoriteDao = LocalDatabase.getAppDatabase(application)?.favoriteDao()
        repository = FavoriteRepository(favoriteDao!!)
        allFavoriteMovieDetail = repository.allFavoriteDetail

    }

    fun insertFavoriteMovieDetail(favoriteMovieEntity: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteMovieDetail(favoriteMovieEntity)
        }
    }

    fun deleteFavoriteMovieDetail(favoriteMovieEntity: FavoriteMovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteMovieDetail(favoriteMovieEntity)
        }
    }
}