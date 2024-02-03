package com.ds.movieapp.domain.repo

import com.ds.movieapp.data.models.ConfigurationDto
import com.ds.movieapp.data.models.Genres
import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.domain.models.MovieDetails
import com.ds.movieapp.domain.models.SearchResult
import kotlinx.coroutines.flow.Flow

interface MoviesRepo {

    suspend fun getGenres(): Genres
    suspend fun getMoviesByGenre(genreId: String): Flow<List<Movie>>
    suspend fun searchMovies(query: String): List<SearchResult>
    suspend fun getMovieById(id: String): Flow<MovieDetails>

    suspend fun getConfiguration(): ConfigurationDto
    fun addToFavorites(movieId: String)
    fun removeFromFavorites(movieId: String)
    fun onCleared()
}
