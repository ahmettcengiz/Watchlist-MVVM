package com.nomadapps.watchlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadapps.watchlist.model.MovieDetail

class MovieDetailViewModel : ViewModel() {
    var movieDetailLiveData: MutableLiveData<MovieDetail>? = null

    fun getMovieDetail(id: String): LiveData<MovieDetail>? {
        movieDetailLiveData = MovieDetailRepository.getMovieDetailApiCall(id)
        return movieDetailLiveData
    }

}