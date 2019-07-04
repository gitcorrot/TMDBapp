package com.corrot.tmdbapp.repository

import com.corrot.tmdbapp.api.MovieResponse
import com.corrot.tmdbapp.api.Result
import com.corrot.tmdbapp.api.TmdbAPI
import java.io.IOException

class TmdbRepository(private val api: TmdbAPI) {

    suspend fun getPopularMovies(): Result<MovieResponse> {
        val postRequest = api.getPopularMovies()
        val response = postRequest.await()

        return if (response.isSuccessful) Result.Success(response.body()!!)
        else Result.Error(IOException(response.message()))
    }

    suspend fun searchMovie(name: String): Result<MovieResponse> {
        val postRequest = api.searchMovie(name)
        val response = postRequest.await()

        return if (response.isSuccessful) Result.Success(response.body()!!)
        else Result.Error(IOException(response.message()))
    }
}
