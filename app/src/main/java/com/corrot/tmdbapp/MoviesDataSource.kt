package com.corrot.tmdbapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.corrot.tmdbapp.api.ApiFactory
import com.corrot.tmdbapp.api.LoadState
import com.corrot.tmdbapp.api.MovieResponse
import com.corrot.tmdbapp.api.TmdbAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesDataSource(
    private val type: MoviesDataFactory.MoviesDataType
) : PageKeyedDataSource<Int, Movie>() {

    private val api: TmdbAPI = ApiFactory.tmdbApi
    val loadingState = MutableLiveData<LoadState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        GlobalScope.launch {
            val response = loadMovies(type, 1)
            response?.let {
                when {
                    it.isSuccessful -> {
                        loadingState.postValue(LoadState.LOADED)
                        callback.onResult(it.body()!!.results, null, 1)
                    }
                    else -> loadingState.postValue(LoadState.FAILED)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        GlobalScope.launch {
            val response = loadMovies(type, params.key)
            response?.let {
                when {
                    it.isSuccessful -> {
                        val nextPage =
                            if (params.key < it.body()!!.total_pages) params.key + 1
                            else null
                        println("LOADING PAGE: " + nextPage.toString())
                        loadingState.postValue(LoadState.LOADED)
                        callback.onResult(it.body()!!.results, nextPage)
                    }
                    else -> loadingState.postValue(LoadState.FAILED)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    private suspend fun loadMovies(
        type: MoviesDataFactory.MoviesDataType,
        page: Int
    ): Response<MovieResponse>? {
        loadingState.postValue(LoadState.LOADING)
        var response: Response<MovieResponse>? = null
        try {
            response = when (type) {
                MoviesDataFactory.MoviesDataType.POPULAR ->
                    api.getPopularMovies(page).await()
                MoviesDataFactory.MoviesDataType.NOW_PLAYING ->
                    api.getNowPlayingMovies(page).await()
                MoviesDataFactory.MoviesDataType.TOP_RATED ->
                    api.getTopRatedMovies(page).await()
                MoviesDataFactory.MoviesDataType.UPCOMING ->
                    api.getUpcomingMovies(page).await()
            }
        } catch (e: Exception) {
            if (e.message.isNullOrBlank())
                Log.e(javaClass.name, e.message!!)
        }
        return response
    }
}
