package com.nomadapps.watchlist.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String,
    val vote_average: Double,
    val backdrop_path: String
)