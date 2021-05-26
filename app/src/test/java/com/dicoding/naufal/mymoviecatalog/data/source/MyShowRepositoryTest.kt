package com.dicoding.naufal.mymoviecatalog.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.naufal.mymoviecatalog.data.FakeMyShowRepository
import com.dicoding.naufal.mymoviecatalog.data.source.local.LocalDataSource
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.data.source.remote.RemoteDataSource
import com.dicoding.naufal.mymoviecatalog.utils.*
import com.dicoding.naufal.mymoviecatalog.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Test

import org.junit.Rule
import org.mockito.Mockito.*
import java.util.concurrent.Executors

class MyShowRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val testExecutors: AppExecutors = AppExecutors(TestExecutor(), TestExecutor(), TestExecutor())
    private val myShowRepository = FakeMyShowRepository(remote, local, appExecutors)

    private val showResponses = DataDummy.generateRemoteDummyShows()
    private val show = DataDummy.generateRemoteDummyShows()[0]
    private val showId = show.showId

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowEntity>
        `when`(local.getMovies()).thenReturn(dataSourceFactory)
        myShowRepository.getAllMovies()

        val movies = ArrayList<ShowEntity>()
        for (showEntity in DataDummy.generateDummyShow()){
            if (showEntity.isMovie){
                movies.add(showEntity)
            }
        }

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(movies))
        verify(local).getMovies()
        assertNotNull(moviesEntities.data)
        assertEquals(movies.size.toLong(), moviesEntities.data?.size?.toLong())
    }

    @Test
    fun getAllSeries() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowEntity>
        `when`(local.getSeries()).thenReturn(dataSourceFactory)
        myShowRepository.getAllSeries()

        val series = ArrayList<ShowEntity>()
        for (showEntity in DataDummy.generateDummyShow()){
            if (!showEntity.isMovie){
                series.add(showEntity)
            }
        }

        val seriesEntities = Resource.success(PagedListUtil.mockPagedList(series))
        verify(local).getSeries()
        assertNotNull(seriesEntities.data)
        assertEquals(series.size.toLong(), seriesEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteMovies(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        myShowRepository.getFavoriteMovies()

        val movies = ArrayList<ShowEntity>()
        for (showEntity in DataDummy.generateDummyShow()){
            if (showEntity.isMovie){
                movies.add(showEntity)
            }
        }

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(movies))
        verify(local).getFavoriteMovies()
        assertNotNull(moviesEntities)
        assertEquals(movies.size.toLong(), moviesEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteSeries(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowEntity>
        `when`(local.getFavoriteSeries()).thenReturn(dataSourceFactory)
        myShowRepository.getFavoriteSeries()

        val series = ArrayList<ShowEntity>()
        for (showEntity in DataDummy.generateDummyShow()){
            if (!showEntity.isMovie){
                series.add(showEntity)
            }
        }

        val seriesEntities = Resource.success(PagedListUtil.mockPagedList(series))
        verify(local).getFavoriteSeries()
        assertNotNull(seriesEntities)
        assertEquals(series.size.toLong(), seriesEntities.data?.size?.toLong())
    }

    @Test
    fun getShowDetail() {
        val dummyEntity = MutableLiveData<ShowEntity>()
        dummyEntity.value = DataDummy.generateDummyShow()[0]
        `when`(local.getShowById(showId)).thenReturn(dummyEntity)

        val showEntity = LiveDataTestUtil.getValue(myShowRepository.getShowDetail(showId))
        verify(local).getShowById(showId)
        assertNotNull(showEntity.data)
        assertNotNull(showEntity.data?.showTitle)
        assertEquals(showResponses[0].showTitle, showEntity.data?.showTitle)
    }

    @Test
    fun setShowFavorite(){
        val dummyEntity = DataDummy.generateDummyShow()[0].copy(isFavorite = false)
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setShowFavorite(dummyEntity, true)
        myShowRepository.setShowFavorite(dummyEntity, true)

        verify(local, times(1)).setShowFavorite(dummyEntity, true)
    }
}