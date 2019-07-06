package com.corrot.tmdbapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.corrot.tmdbapp.api.ApiFactory
import com.corrot.tmdbapp.api.LoadState
import com.corrot.tmdbapp.api.TmdbAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviesDataSource : PageKeyedDataSource<Int, Movie>() {

    private val api: TmdbAPI = ApiFactory.tmdbApi
    val loadingState = MutableLiveData<LoadState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        loadingState.postValue(LoadState.LOADING)
        GlobalScope.launch {
            try {
                val response = api.getPopularMovies(1).await()
                when {
                    response.isSuccessful -> {
                        loadingState.postValue(LoadState.LOADED)
                        callback.onResult(response.body()!!.results, null, 1)
                    }
                    else -> loadingState.postValue(LoadState.FAILED)
                }
            } catch (e: Exception) {
                if (e.message.isNullOrBlank())
                    Log.e(javaClass.name, e.message!!)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        loadingState.postValue(LoadState.LOADING)
        GlobalScope.launch {
            try {
                val response = api.getPopularMovies(params.key).await()
                when {
                    response.isSuccessful -> {
                        val nextPage =
                            if (params.key < response.body()!!.total_pages) params.key + 1
                            else null
                        println("LOADING PAGE: " + nextPage.toString())
                        loadingState.postValue(LoadState.LOADED)
                        callback.onResult(response.body()!!.results, nextPage)
                    }
                    else -> loadingState.postValue(LoadState.FAILED)
                }
            } catch (e: Exception) {
                if (e.message.isNullOrBlank())
                    Log.e(javaClass.name, e.message!!)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}
}
