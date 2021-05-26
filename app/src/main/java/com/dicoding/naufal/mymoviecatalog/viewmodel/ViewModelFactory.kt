package com.dicoding.naufal.mymoviecatalog.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.naufal.mymoviecatalog.data.MyShowRepository
import com.dicoding.naufal.mymoviecatalog.di.Injection
import com.dicoding.naufal.mymoviecatalog.ui.detail.DetailViewModel
import com.dicoding.naufal.mymoviecatalog.ui.favorite.FavoriteViewModel
import com.dicoding.naufal.mymoviecatalog.ui.show.ShowViewModel

class ViewModelFactory private constructor(private val myShowRepository: MyShowRepository): ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(ShowViewModel::class.java) -> {
                return ShowViewModel(myShowRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(myShowRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                return  FavoriteViewModel(myShowRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}