package com.dicoding.naufal.mymoviecatalog.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.data.source.local.room.ShowDao

class LocalDataSource private constructor(private val mShowDao: ShowDao){
    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(showDao: ShowDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(showDao)
    }

    fun getMovies(): DataSource.Factory<Int, ShowEntity> = mShowDao.getMovies()

    fun getSeries(): DataSource.Factory<Int, ShowEntity> = mShowDao.getSeries()

    fun getFavoriteMovies(): DataSource.Factory<Int, ShowEntity> = mShowDao.getFavoriteMovies()

    fun getFavoriteSeries(): DataSource.Factory<Int, ShowEntity> = mShowDao.getFavoriteSeries()

    fun getShowById(showId: String): LiveData<ShowEntity> = mShowDao.getShowById(showId)

    fun insertShows(shows: List<ShowEntity>) = mShowDao.insertShows(shows)

    fun insertShow(show: ShowEntity) = mShowDao.insertShow(show)

    fun setShowFavorite(show: ShowEntity, newState: Boolean){
        show.isFavorite = newState
        mShowDao.updateShow(show)
    }
}