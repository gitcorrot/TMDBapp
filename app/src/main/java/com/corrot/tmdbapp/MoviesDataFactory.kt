package com.corrot.tmdbapp

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

// responsible for creating MoviesDataSource
class MoviesDataFactory : DataSource.Factory<Int, Movie>() {

    val mutableDataSource = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource = MoviesDataSource()
        mutableDataSource.postValue(moviesDataSource)
        return moviesDataSource
    }
}
