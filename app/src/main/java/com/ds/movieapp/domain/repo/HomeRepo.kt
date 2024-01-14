package com.ds.movieapp.domain.repo

import com.ds.movieapp.data.models.ConfigurationDto
import com.ds.movieapp.data.models.Genres
import com.ds.movieapp.domain.models.Movie

interface HomeRepo {

    suspend fun getGenres(): Genres
    suspend fun getMoviesByGenre(genreId: String): List<Movie>

    suspend fun getConfiguration(): ConfigurationDto
    fun onCleared()
}
