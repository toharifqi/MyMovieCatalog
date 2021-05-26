package com.dicoding.naufal.mymoviecatalog.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.naufal.mymoviecatalog.data.MyShowRepository
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest{

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myShowRepository: MyShowRepository

    @Mock
    private lateinit var observer: Observer<PagedList<ShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<ShowEntity>

    @Before
    fun setUp(){
        viewModel = FavoriteViewModel(myShowRepository)
    }

    @Test
    fun testGetFavoriteMovies() {
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(10)
        val movies = MutableLiveData<PagedList<ShowEntity>>()
        movies.value = dummyMovies

        `when`(myShowRepository.getFavoriteMovies()).thenReturn(movies)
        val favoriteMovies = viewModel.getFavoriteMovies().value
        verify(myShowRepository).getFavoriteMovies()
        assertNotNull(favoriteMovies)
        assertEquals(10, favoriteMovies?.size)

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun testGetFavoriteSeries() {
        val dummySeries = pagedList
        `when`(dummySeries.size).thenReturn(10)
        val series = MutableLiveData<PagedList<ShowEntity>>()
        series.value = dummySeries

        `when`(myShowRepository.getFavoriteSeries()).thenReturn(series)
        val favoriteSeries = viewModel.getFavoriteSeries().value
        verify(myShowRepository).getFavoriteSeries()
        assertNotNull(favoriteSeries)
        assertEquals(10, favoriteSeries?.size)

        viewModel.getFavoriteSeries().observeForever(observer)
        verify(observer).onChanged(dummySeries)
    }
}