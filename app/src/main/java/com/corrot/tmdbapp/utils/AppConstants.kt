package com.corrot.tmdbapp.utils

import com.corrot.tmdb_app.BuildConfig

class AppConstants {

    companion object {
        var TMDB_API_KEY = BuildConfig.TMDB_API_KEY

        var POSTER_92_BASE_URL = "http://image.tmdb.org/t/p/w92/"
        var POSTER_154_BASE_URL = "http://image.tmdb.org/t/p/w154/"
        var POSTER_342_BASE_URL = "http://image.tmdb.org/t/p/w342/"
    }
}