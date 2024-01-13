package com.ds.movieapp.domain.repo

import com.ds.movieapp.data.models.Genres
interface HomeRepo {

    suspend fun getGenres(): Genres
    fun onCleared()
}
