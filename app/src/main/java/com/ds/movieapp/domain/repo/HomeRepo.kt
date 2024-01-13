package com.ds.movieapp.domain.repo

import com.ds.movieapp.data.models.Genres
import com.ds.movieapp.data.models.MoviesDto

interface HomeRepo {

    suspend fun getGenres(): Genres
    suspend fun getMoviesByGenre(genreId: String): MoviesDto
    fun onCleared()
}
