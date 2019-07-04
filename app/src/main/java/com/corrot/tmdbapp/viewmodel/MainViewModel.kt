package com.corrot.tmdbapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corrot.tmdbapp.Page
import com.corrot.tmdbapp.Movie
import com.corrot.tmdbapp.api.ApiFactory
import com.corrot.tmdbapp.api.Result
import com.corrot.tmdbapp.repository.TmdbRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository =
        TmdbRepository(ApiFactory.tmdbApi)

    val popularMoviesLiveData = MutableLiveData<List<Movie>>()
    val popularMoviesPageLiveData = MutableLiveData<Page>()

    fun fetchMovies() {
        viewModelScope.launch {
            when (val result = repository.getPopularMovies()) {
                is Result.Success -> {
                    popularMoviesLiveData.postValue(result.data.results)
                    popularMoviesPageLiveData.postValue(
                        Page(
                            result.data.page,
                            result.data.total_pages
                        )
                    )
                }
                is Result.Error ->
                    throw result.exception
            }
        }
    }
}
