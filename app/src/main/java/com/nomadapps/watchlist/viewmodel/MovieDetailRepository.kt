package com.nomadapps.watchlist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nomadapps.watchlist.api.ApiClient
import com.nomadapps.watchlist.model.MovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieDetailRepository {
    private const val API_KEY = "76d1caf3e6b83eaaa662ea3bb10873ed"
    val gameDetail = MutableLiveData<MovieDetail>()

    fun getMovieDetailApiCall(id: String): MutableLiveData<MovieDetail> {

        val call = ApiClient.apiInterface.getMovieDetail(id.toInt(),API_KEY)

        call.enqueue(object: Callback<MovieDetail> {
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.e("hahaDEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<MovieDetail>,
                response: Response<MovieDetail>
            ) {
                gameDetail.value = response.body()

            }
        })

        return gameDetail
    }

}