package com.corrot.tmdbapp.api

import com.corrot.tmdbapp.TmdbMovie
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface TmdbAPI {
    @GET("movie/popular")
    fun getPopularMovies(): Deferred<Response<TmdbMovieResponse>>
}

data class TmdbMovieResponse(
    val results: List<TmdbMovie>
)
