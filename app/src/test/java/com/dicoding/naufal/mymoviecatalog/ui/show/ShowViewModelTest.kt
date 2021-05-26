package com.dicoding.naufal.mymoviecatalog.ui.show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.data.MyShowRepository
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.naufal.mymoviecatalog.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowViewModelTest {

    private lateinit var viewModel: ShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myShowRepository: MyShowRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<ShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<ShowEntity>

    @Before
    fun setUp(){
        viewModel = ShowViewModel(myShowRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(10)
        val movies = MutableLiveData<Resource<PagedList<ShowEntity>>>()
        movies.value = dummyMovies

        `when`(myShowRepository.getAllMovies()).thenReturn(movies)
        val moviesEntities = viewModel.getMovies().value?.data
        verify(myShowRepository).getAllMovies()
        assertNotNull(moviesEntities)
        assertEquals(10, moviesEntities?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getSeries() {
        val dummySeries = Resource.success(pagedList)
        `when`(dummySeries.data?.size).thenReturn(10)
        val series = MutableLiveData<Resource<PagedList<ShowEntity>>>()
        series.value = dummySeries

        `when`(myShowRepository.getAllSeries()).thenReturn(series)
        val seriesEntities = viewModel.getSeries().value?.data
        verify(myShowRepository).getAllSeries()
        assertNotNull(seriesEntities)
        assertEquals(10, seriesEntities?.size)

        viewModel.getSeries().observeForever(observer)
        verify(observer).onChanged(dummySeries)
    }
}