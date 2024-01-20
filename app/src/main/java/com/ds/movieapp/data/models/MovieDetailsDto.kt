package com.ds.movieapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerialName("imdb_id")
    val imdbId: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)
