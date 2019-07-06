package com.corrot.tmdbapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corrot.tmdbapp.MoviesDataFactory

class MoviesViewModelFactory(
    private val type: MoviesDataFactory.MoviesDataType
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(type) as T
    }
}
