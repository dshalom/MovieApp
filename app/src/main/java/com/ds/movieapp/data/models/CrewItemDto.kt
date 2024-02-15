package com.ds.movieapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewItemDto(
    val name: String,
    val job: String,
    @SerialName("profile_path")
    val profilePath: String?
)
