package com.ds.movieapp.domain.models

data class MovieDetails(
    val id: String,
    val title: String,
    val backdropPath: String,
    var isFavourite: Boolean = false
)
