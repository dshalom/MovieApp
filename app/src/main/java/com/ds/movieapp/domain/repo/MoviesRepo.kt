package com.ds.movieapp.domain.repo

import com.ds.movieapp.data.models.ConfigurationDto
import com.ds.movieapp.data.models.Genres
import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.domain.models.MovieDetails

interface MoviesRepo {

    suspend fun getGenres(): Genres
    suspend fun getMoviesByGenre(genreId: String): List<Movie>

    suspend fun getMovieById(id: String): MovieDetails

    suspend fun getConfiguration(): ConfigurationDto
    fun onCleared()
}
