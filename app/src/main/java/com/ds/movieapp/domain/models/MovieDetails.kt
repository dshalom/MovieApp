package com.ds.movieapp.domain.models

data class MovieDetails(
    val id: String,
    val title: String,
    val director: String?,
    val tagline: String,
    val overview: String,
    val genres: List<String>,
    val voteAverage: Float,
    val backdropPath: String,
    var isFavourite: Boolean = false,
    val cast: List<CastItem>
)
