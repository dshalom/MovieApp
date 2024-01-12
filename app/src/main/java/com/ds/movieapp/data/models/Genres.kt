package com.ds.movieapp.data.models

import com.ds.movieapp.BuildConfig
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

@Serializable
data class DSR(
    val username: String = BuildConfig.USERNAME,
    val password: String = BuildConfig.PASSWORD
)
