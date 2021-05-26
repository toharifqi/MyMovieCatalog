package com.dicoding.naufal.mymoviecatalog.di

import android.content.Context
import com.dicoding.naufal.mymoviecatalog.data.MyShowRepository
import com.dicoding.naufal.mymoviecatalog.data.source.local.LocalDataSource
import com.dicoding.naufal.mymoviecatalog.data.source.local.room.ShowDatabase
import com.dicoding.naufal.mymoviecatalog.data.source.remote.RemoteDataSource
import com.dicoding.naufal.mymoviecatalog.utils.AppExecutors
import com.dicoding.naufal.mymoviecatalog.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): MyShowRepository {
        val database = ShowDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.showDao())
        val appExecutors = AppExecutors()
        return MyShowRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}