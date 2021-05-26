package com.dicoding.naufal.mymoviecatalog.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity

@Dao
interface ShowDao {

    @Query("SELECT * FROM showentities WHERE isMovie = 1")
    fun getMovies(): DataSource.Factory<Int, ShowEntity>

    @Query("SELECT * FROM showentities WHERE isMovie = 0")
    fun getSeries(): DataSource.Factory<Int, ShowEntity>

    @Query("SELECT * FROM showentities WHERE isFavorite = 1 AND isMovie = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, ShowEntity>

    @Query("SELECT * FROM showentities WHERE isFavorite = 1 AND isMovie = 0")
    fun getFavoriteSeries(): DataSource.Factory<Int, ShowEntity>

    @Query("SELECT * FROM showentities WHERE showId = :showId")
    fun getShowById(showId: String): LiveData<ShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShows(shows: List<ShowEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertShow(show: ShowEntity)

    @Update
    fun updateShow(show: ShowEntity)
}