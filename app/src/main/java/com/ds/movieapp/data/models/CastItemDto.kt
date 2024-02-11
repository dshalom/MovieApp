package com.ds.movieapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastItemDto(
    @SerialName("cast_id")
    val castId: Int,
    val character: String,
    @SerialName("credit_id")
    val creditId: String,
    val id: Int,
    val name: String,
    val order: Int,
    @SerialName("profile_path")
    val profilePath: String?
)
