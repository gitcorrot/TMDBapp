package com.corrot.tmdbapp.repository

import com.corrot.tmdbapp.api.Result
import com.corrot.tmdbapp.api.TmdbAPI
import com.corrot.tmdbapp.api.TmdbMovieResponse
import java.io.IOException

class TmdbRepository(private val api: TmdbAPI) {

    suspend fun getPopularMovies(): Result<TmdbMovieResponse> {
        val postRequest = api.getPopularMovies()
        val response = postRequest.await()

        return if (response.isSuccessful) Result.Success(response.body()!!)
        else Result.Error(IOException(response.message()))
    }
}
