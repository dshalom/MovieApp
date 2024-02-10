package com.ds.movieapp.domain.models

data class MovieDetails(
    val id: String,
    val title: String,
    val tagline: String,
    val overview: String,
    var genres: List<String>,
    var voteAverage: Float,
    val backdropPath: String,
    var isFavourite: Boolean = false
)
