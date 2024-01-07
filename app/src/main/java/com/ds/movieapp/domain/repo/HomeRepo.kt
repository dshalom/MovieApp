package com.ds.movieapp.domain.repo

import com.ds.movieapp.domain.models.Genres
interface HomeRepo {

    suspend fun getGenres(): Genres
    fun onCleared()
}
