package com.ds.movieapp.domain

import com.ds.movieapp.domain.models.Genres
interface Repo {

    suspend fun getGenres(): Genres
    fun onCleared()
}
