package com.nomadapps.watchlist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nomadapps.watchlist.api.ApiClient
import com.nomadapps.watchlist.model.MovieModel
import com.nomadapps.watchlist.model.SeriesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieListRepository {
    private const val API_KEY = "76d1caf3e6b83eaaa662ea3bb10873ed"
    val movieList = MutableLiveData<MovieModel>()
    val moreLikeThisList = MutableLiveData<MovieModel>()
    val similarShows = MutableLiveData<SeriesModel>()
    val serieList = MutableLiveData<SeriesModel>()

    fun getMovieListApiCall(): MutableLiveData<MovieModel> {
        val call = ApiClient.apiInterface.getMovieList(API_KEY, 1)

        call.enqueue(object : Callback<MovieModel> {
            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.e("DEBUG:", t.message.toString())
            }

            override fun onResponse(
                call: Call<MovieModel>,
                response: Response<MovieModel>
            ) {
                movieList.value = response.body()

            }
        })

        return movieList
    }

    fun getMoreLikeThisListApiCall(id: String): MutableLiveData<MovieModel> {
        val call = ApiClient.apiInterface.getMoreLikeThisList(id.toInt(), API_KEY)

        call.enqueue(object : Callback<MovieModel> {
            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.e("getMoreLikeThisListApi", t.message.toString())
            }

            override fun onResponse(
                call: Call<MovieModel>,
                response: Response<MovieModel>
            ) {
                moreLikeThisList.value = response.body()

            }
        })

        return moreLikeThisList
    }

    fun getMoreLikeThisSerieListApiCall(id: String): MutableLiveData<SeriesModel> {
        val call = ApiClient.apiInterface.getSimilarShows(id.toInt(), API_KEY)

        call.enqueue(object : Callback<SeriesModel> {
            override fun onFailure(call: Call<SeriesModel>, t: Throwable) {
                Log.e("getMoreLikeThisListApi", t.message.toString())
            }

            override fun onResponse(
                call: Call<SeriesModel>,
                response: Response<SeriesModel>
            ) {
                similarShows.value = response.body()

            }
        })

        return similarShows
    }

    fun getSerieListApiCall(): MutableLiveData<SeriesModel> {
        val call = ApiClient.apiInterface.getSerieList(API_KEY, 1)

        call.enqueue(object : Callback<SeriesModel> {
            override fun onFailure(call: Call<SeriesModel>, t: Throwable) {
                Log.e("getSerieList", t.message.toString())
            }

            override fun onResponse(
                call: Call<SeriesModel>,
                response: Response<SeriesModel>
            ) {
                serieList.value = response.body()

            }
        })

        return serieList
    }
}