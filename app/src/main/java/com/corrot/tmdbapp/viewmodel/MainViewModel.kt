package com.corrot.tmdbapp.viewmodel

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corrot.tmdbapp.api.ApiFactory
import com.corrot.tmdbapp.api.Result
import com.corrot.tmdbapp.repository.TmdbRepository
import com.corrot.tmdbapp.utils.notifyObserver
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository =
        TmdbRepository(ApiFactory.tmdbApi)

    private var userQueryInput = MutableLiveData<String>()
    var userQueryOutput = MediatorLiveData<List<String>>()

    init {
        userQueryOutput.addSource(userQueryInput) { searchMovies(it) }
    }

    private fun searchMovies(name: String) {
        viewModelScope.launch {
            when (val result = repository.searchMovie(name)) {
                is Result.Success -> {
                    userQueryOutput.value = result.data.results.map { it.title }
                    userQueryOutput.notifyObserver()
                }
                is Result.Error ->
                    Log.e(javaClass.name, result.exception.message!!)
                //throw result.exception
            }
        }
    }

    fun setQueryInput(input: String) {
        userQueryInput.value = input
    }
}
