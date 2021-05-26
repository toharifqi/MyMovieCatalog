package com.dicoding.naufal.mymoviecatalog.data.source.remote

import android.os.Looper
import com.dicoding.naufal.mymoviecatalog.utils.JsonHelper
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.naufal.mymoviecatalog.data.source.remote.response.ShowResponse
import com.dicoding.naufal.mymoviecatalog.utils.EspressoIdlingResources

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){
    private val handler = Handler(Looper.getMainLooper())

    companion object{
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies(): LiveData<ApiResponse<List<ShowResponse>>>{
        EspressoIdlingResources.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<ShowResponse>>>()
        handler.postDelayed({
            resultMovies.value = ApiResponse.success(jsonHelper.loadMovies())
            EspressoIdlingResources.decrement()
                            }, SERVICE_LATENCY_IN_MILLIS)
        return resultMovies
    }

    fun getAllSeries(): LiveData<ApiResponse<List<ShowResponse>>>{
        EspressoIdlingResources.increment()
        val resultSeries = MutableLiveData<ApiResponse<List<ShowResponse>>>()
        handler.postDelayed({
            resultSeries.value = ApiResponse.success(jsonHelper.loadSeries())
            EspressoIdlingResources.decrement()
                            }, SERVICE_LATENCY_IN_MILLIS)
        return resultSeries
    }

    fun getShowDetail(showId: String): LiveData<ApiResponse<ShowResponse>>{
        EspressoIdlingResources.increment()
        val resultShowDetail = MutableLiveData<ApiResponse<ShowResponse>>()
        handler.postDelayed({
            resultShowDetail.value = ApiResponse.success(jsonHelper.loadShowDetail(showId))
            EspressoIdlingResources.decrement()
                            }, SERVICE_LATENCY_IN_MILLIS)
        return resultShowDetail
    }
}