package com.corrot.tmdbapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corrot.tmdbapp.TmdbMovie
import com.corrot.tmdbapp.repository.TmdbRepository
import com.corrot.tmdbapp.api.ApiFactory
import com.corrot.tmdbapp.api.Result
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository =
        TmdbRepository(ApiFactory.tmdbApi)

    val popularMoviesLiveData = MutableLiveData<List<TmdbMovie>>()

    fun fetchMovies() {
        viewModelScope.launch {
            when (val result = repository.getPopularMovies()) {
                is Result.Success ->
                    popularMoviesLiveData.postValue(result.data.results)
                is Result.Error ->
                    throw result.exception
            }
        }
    }
}
