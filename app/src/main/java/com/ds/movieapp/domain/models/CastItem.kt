package com.ds.movieapp.domain.models

data class CastItem(
    val castId: Int,
    val character: String,
    val creditId: String,
    val id: Int,
    val name: String,
    val order: Int,
    val profilePath: String?
)
