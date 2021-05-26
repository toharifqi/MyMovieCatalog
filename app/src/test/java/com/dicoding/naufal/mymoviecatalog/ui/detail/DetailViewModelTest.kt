package com.dicoding.naufal.mymoviecatalog.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.data.MyShowRepository
import com.dicoding.naufal.mymoviecatalog.utils.DataDummy
import com.dicoding.naufal.mymoviecatalog.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private val dummyShow = DataDummy.generateDummyShow()[0]
    private val showId = dummyShow.showId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var myShowRepository: MyShowRepository

    @Mock
    private lateinit var observer: Observer<Resource<ShowEntity>>

    @Before
    fun setUp(){
        viewModel = DetailViewModel(myShowRepository)
        viewModel.setShowId(showId)
    }

    @Test
    fun getDetailShow() {
        val dummyEntity = Resource.success(DataDummy.generateDummyShow()[0])
        val show = MutableLiveData<Resource<ShowEntity>>()
        show.value = dummyEntity

        `when`(myShowRepository.getShowDetail(showId)).thenReturn(show)

        viewModel.show.observeForever(observer)
        verify(observer).onChanged(dummyEntity)
    }

    @Test
    fun setFavorite(){
        val dummyEntity = Resource.success(DataDummy.generateDummyShow()[0])
        val show = MutableLiveData<Resource<ShowEntity>>()
        show.value = dummyEntity

        `when`(myShowRepository.getShowDetail(showId)).thenReturn(show)
        viewModel.show = myShowRepository.getShowDetail(showId)
        viewModel.setFavorite()

        verify(myShowRepository).setShowFavorite(show.value?.data as ShowEntity, true)
        verifyNoMoreInteractions(observer)
    }

}