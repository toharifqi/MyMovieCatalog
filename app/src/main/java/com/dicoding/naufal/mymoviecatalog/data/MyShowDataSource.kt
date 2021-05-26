package com.dicoding.naufal.mymoviecatalog.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.vo.Resource

interface MyShowDataSource {
    fun getAllMovies(): LiveData<Resource<PagedList<ShowEntity>>>

    fun getAllSeries(): LiveData<Resource<PagedList<ShowEntity>>>

    fun getShowDetail(showId: String): LiveData<Resource<ShowEntity>>

    fun setShowFavorite(show: ShowEntity, state: Boolean)

    fun getFavoriteMovies(): LiveData<PagedList<ShowEntity>>

    fun getFavoriteSeries(): LiveData<PagedList<ShowEntity>>
}