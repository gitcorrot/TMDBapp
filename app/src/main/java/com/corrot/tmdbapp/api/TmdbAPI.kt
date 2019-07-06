package com.corrot.tmdbapp.api

import com.corrot.tmdbapp.Movie
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("page") page: Int
    ): Deferred<Response<MovieResponse>>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int
    ): Deferred<Response<MovieResponse>>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("page") page: Int
    ): Deferred<Response<MovieResponse>>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") page: Int
    ): Deferred<Response<MovieResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Deferred<Response<Movie>>

    @GET("search/movie")
    fun searchMovie(
        @Query("query") name: String
    ): Deferred<Response<MovieResponse>>
}

data class MovieResponse(
    val page: Int,
    val total_pages: Int,
    val results: List<Movie>
)
