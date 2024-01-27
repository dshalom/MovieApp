package com.ds.movieapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: String,
    val name: String
)
