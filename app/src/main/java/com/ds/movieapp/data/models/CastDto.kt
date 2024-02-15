package com.ds.movieapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CastDto(
    val cast: List<CastItemDto>,
    val crew: List<CrewItemDto>
)
