package com.ds.movieapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MoviesDto(
    val results: List<MovieDto>
)
