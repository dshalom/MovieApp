package com.ds.movieapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Genres(
    val genres: List<Genre>
)
