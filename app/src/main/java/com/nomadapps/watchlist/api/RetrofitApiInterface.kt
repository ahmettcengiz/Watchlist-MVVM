package com.nomadapps.watchlist.api

import com.nomadapps.watchlist.model.MovieDetail
import com.nomadapps.watchlist.model.MovieModel
import com.nomadapps.watchlist.model.SeriesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiInterface {

    @GET("movie/popular")
    fun getMovieList(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Call<MovieModel>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDetail>

    @GET("movie/{movie_id}/similar")
    fun getMoreLikeThisList(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
    ): Call<MovieModel>

    @GET("tv/{tv_id}/similar")
    fun getSimilarShows(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String,
    ): Call<SeriesModel>

    @GET("tv/popular")
    fun getSerieList(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Call<SeriesModel>
}