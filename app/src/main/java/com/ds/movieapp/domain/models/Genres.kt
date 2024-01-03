package com.ds.movieapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Genres(
    val genres: List<Genre>
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)
