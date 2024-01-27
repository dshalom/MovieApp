package com.ds.movieapp.domain.models

data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<String>,
    val id: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    var isFavourite: Boolean
)
