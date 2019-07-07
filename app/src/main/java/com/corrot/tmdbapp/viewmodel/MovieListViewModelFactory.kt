package com.corrot.tmdbapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corrot.tmdbapp.paging.MoviesDataFactory

class MovieListViewModelFactory(
    private val type: MoviesDataFactory.MoviesDataType
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieListViewModel(type) as T
    }
}
