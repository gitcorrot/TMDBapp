package com.corrot.tmdbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.corrot.tmdbapp.Movie
import com.corrot.tmdbapp.MoviesDataFactory
import com.corrot.tmdbapp.api.LoadState

class MoviesViewModel(
    private val type: MoviesDataFactory.MoviesDataType
) : ViewModel() {

    var loadingState: LiveData<LoadState>
    var popularMoviesLiveData: LiveData<PagedList<Movie>>

    init {
        val movesDataFactory = MoviesDataFactory(type)

        loadingState = Transformations.switchMap(movesDataFactory.mutableDataSource) {
            it.loadingState
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
