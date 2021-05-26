package com.dicoding.naufal.mymoviecatalog.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.naufal.mymoviecatalog.data.MyShowRepository
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity

class FavoriteViewModel(private val myShowRepository: MyShowRepository): ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<ShowEntity>> = myShowRepository.getFavoriteMovies()

    fun getFavoriteSeries(): LiveData<PagedList<ShowEntity>> = myShowRepository.getFavoriteSeries()

    fun setFavorite(showEntity: ShowEntity){
        val newState = !showEntity.isFavorite
        myShowRepository.setShowFavorite(showEntity, newState)
    }
}