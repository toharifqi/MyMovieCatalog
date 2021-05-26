package com.dicoding.naufal.mymoviecatalog.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.naufal.mymoviecatalog.data.source.local.LocalDataSource
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.data.source.remote.ApiResponse
import com.dicoding.naufal.mymoviecatalog.data.source.remote.RemoteDataSource
import com.dicoding.naufal.mymoviecatalog.data.source.remote.response.ShowResponse
import com.dicoding.naufal.mymoviecatalog.utils.AppExecutors
import com.dicoding.naufal.mymoviecatalog.vo.Resource

class FakeMyShowRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): MyShowDataSource {
    override fun getAllMovies(): LiveData<Resource<PagedList<ShowEntity>>> {
        return object : NetworkBoundResource<PagedList<ShowEntity>, List<ShowResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<ShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<ShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ShowResponse>>> =
                remoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<ShowResponse>) {
                val movieList = ArrayList<ShowEntity>()
                for (response in data){
                    val movie = ShowEntity(response.showId,
                        response.showTitle,
                        response.showReleaseDate,
                        response.showWriter,
                        response.showGenre,
                        response.showSinopsys,
                        response.showPosterPath,
                        response.showScene1Path,
                        response.showScene2Path,
                        response.showScene3Path,
                        response.showStars,
                        true,
                        false)
                    movieList.add(movie)
                }
                localDataSource.insertShows(movieList)
            }

        }.asLiveData()
    }

    override fun getAllSeries(): LiveData<Resource<PagedList<ShowEntity>>> {
        return object : NetworkBoundResource<PagedList<ShowEntity>, List<ShowResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<ShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getSeries(), config).build()
            }


            override fun shouldFetch(data: PagedList<ShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ShowResponse>>> =
                remoteDataSource.getAllSeries()

            override fun saveCallResult(data: List<ShowResponse>) {
                val seriesList = ArrayList<ShowEntity>()
                for (response in data){
                    val series = ShowEntity(response.showId,
                        response.showTitle,
                        response.showReleaseDate,
                        response.showWriter,
                        response.showGenre,
                        response.showSinopsys,
                        response.showPosterPath,
                        response.showScene1Path,
                        response.showScene2Path,
                        response.showScene3Path,
                        response.showStars,
                        false,
                        false)
                    seriesList.add(series)
                }
                localDataSource.insertShows(seriesList)
            }
        }.asLiveData()
    }

    override fun getShowDetail(showId: String): LiveData<Resource<ShowEntity>> {
        return object : NetworkBoundResource<ShowEntity, ShowResponse>(appExecutors){
            override fun loadFromDB(): LiveData<ShowEntity> =
                localDataSource.getShowById(showId)

            override fun shouldFetch(data: ShowEntity?): Boolean =
                data?.showId == null

            override fun createCall(): LiveData<ApiResponse<ShowResponse>> =
                remoteDataSource.getShowDetail(showId)

            override fun saveCallResult(data: ShowResponse) {
                val showDetail = ShowEntity(data.showId,
                    data.showTitle,
                    data.showReleaseDate,
                    data.showWriter,
                    data.showGenre,
                    data.showSinopsys,
                    data.showPosterPath,
                    data.showScene1Path,
                    data.showScene2Path,
                    data.showScene3Path,
                    data.showStars,
                    data.isMovie,
                    false)
                localDataSource.insertShow(showDetail)
            }
        }.asLiveData()
    }

    override fun setShowFavorite(show: ShowEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setShowFavorite(show, state) }
    }

    override fun getFavoriteMovies(): LiveData<PagedList<ShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoriteSeries(): LiveData<PagedList<ShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteSeries(), config).build()
    }
}