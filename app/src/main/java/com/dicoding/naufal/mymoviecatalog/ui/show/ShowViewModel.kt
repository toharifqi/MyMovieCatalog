package com.dicoding.naufal.mymoviecatalog.ui.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.naufal.mymoviecatalog.data.MyShowRepository
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.vo.Resource

class ShowViewModel(private val myShowRepository: MyShowRepository): ViewModel() {

    fun getSeries(): LiveData<Resource<PagedList<ShowEntity>>> = myShowRepository.getAllSeries()

    fun getMovies(): LiveData<Resource<PagedList<ShowEntity>>> = myShowRepository.getAllMovies()
}