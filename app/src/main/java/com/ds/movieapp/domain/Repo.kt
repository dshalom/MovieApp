package com.ds.movieapp.domain

import com.ds.movieapp.data.Resource
import com.ds.movieapp.domain.models.Genres
import kotlinx.coroutines.flow.Flow
interface Repo {

    suspend fun getGenres(): Flow<Resource<Genres>>
}
