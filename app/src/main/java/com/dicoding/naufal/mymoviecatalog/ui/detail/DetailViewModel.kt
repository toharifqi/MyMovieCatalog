package com.dicoding.naufal.mymoviecatalog.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.naufal.mymoviecatalog.data.MyShowRepository
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.vo.Resource

class DetailViewModel(private val myShowRepository: MyShowRepository): ViewModel() {
    val showId = MutableLiveData<String>()

    fun setShowId(showId: String){
        this.showId.value = showId
    }

    var show: LiveData<Resource<ShowEntity>> = Transformations.switchMap(showId){ showId ->
        myShowRepository.getShowDetail(showId)
    }

    fun setFavorite(){
        val showResource = show.value
        if (showResource != null){
            val showEntity = showResource.data
            if (showEntity != null){
                val newState = !showEntity.isFavorite
                myShowRepository.setShowFavorite(showEntity, newState)
            }
        }
    }
}