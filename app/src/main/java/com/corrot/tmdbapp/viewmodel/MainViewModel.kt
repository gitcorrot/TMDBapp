package com.corrot.tmdbapp.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corrot.tmdbapp.Movie
import com.corrot.tmdbapp.Page
import com.corrot.tmdbapp.api.ApiFactory
import com.corrot.tmdbapp.api.Result
import com.corrot.tmdbapp.notifyObserver
import com.corrot.tmdbapp.repository.TmdbRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository =
        TmdbRepository(ApiFactory.tmdbApi)

    val popularMoviesLiveData = MutableLiveData<List<Movie>>()
    val popularMoviesPageLiveData = MutableLiveData<Page>()

    private var userQueryInput = MutableLiveData<String>()
    var userQueryOutput = MediatorLiveData<List<String>>()

    init {
        userQueryOutput.addSource(userQueryInput) { searchMovies(it) }
    }

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
                    popularMoviesLiveData.notifyObserver()
                    popularMoviesPageLiveData.notifyObserver()
                }
                is Result.Error ->
                    throw result.exception
            }
        }
    }

    private fun searchMovies(name: String) {
        viewModelScope.launch {
            when (val result = repository.searchMovie(name)) {
                is Result.Success -> {
                    userQueryOutput.value = result.data.results.map { it.title }
                    userQueryOutput.notifyObserver()
                }
                is Result.Error ->
                    throw result.exception
            }
        }
    }

    fun setQueryInput(input: String) {
        userQueryInput.value = input
    }
}
