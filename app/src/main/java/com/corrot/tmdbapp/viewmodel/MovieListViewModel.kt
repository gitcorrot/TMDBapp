package com.corrot.tmdbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.corrot.tmdbapp.Movie
import com.corrot.tmdbapp.api.LoadState
import com.corrot.tmdbapp.paging.MoviesDataFactory

class MovieListViewModel(
    type: MoviesDataFactory.MoviesDataType
) : ViewModel() {

    var popularMoviesLiveData: LiveData<PagedList<Movie>>
    var loadingState: LiveData<LoadState>
    var totalPages: LiveData<Int>

    init {
        val movesDataFactory = MoviesDataFactory(type)

        loadingState = Transformations.switchMap(movesDataFactory.mutableDataSource) {
            it.loadingState
        }

        totalPages = Transformations.switchMap(movesDataFactory.mutableDataSource) {
            it.totalPages
        }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()

        popularMoviesLiveData = LivePagedListBuilder(movesDataFactory, pagedListConfig)
            .build()
    }
}
