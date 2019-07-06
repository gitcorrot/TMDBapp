package com.corrot.tmdbapp

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

// responsible for creating MoviesDataSource
class MoviesDataFactory(private val type: MoviesDataType) : DataSource.Factory<Int, Movie>() {

    enum class MoviesDataType {
        NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING
    }

    val mutableDataSource = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource = MoviesDataSource(type)
        mutableDataSource.postValue(moviesDataSource)
        return moviesDataSource
    }
}
