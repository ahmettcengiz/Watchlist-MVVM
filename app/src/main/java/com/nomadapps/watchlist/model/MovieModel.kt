package com.nomadapps.watchlist.model

data class MovieModel(
    val results: List<Result>
)

data class Result(
    val id: Int,
    val title: String,
    val release_date: String,
    val poster_path: String,
    val vote_average: Double,
    val backdrop_path: String,
    val overview:String
)