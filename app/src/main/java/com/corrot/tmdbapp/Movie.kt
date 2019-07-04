package com.corrot.tmdbapp

data class Movie(
    val id: Int,
    val title: String,
    val release_date: String,
    val vote_average: Float,
    val overview: String,
    val poster_path: String
)
