package com.ds.movieapp.domain.repo

import kotlinx.coroutines.flow.Flow

data class MovieFavourite(val movieId: String)

interface WatchListFavoritesRepo {

    fun addToFavorites(movieId: String)
    fun removeFromFavorites(movieId: String)
    fun observeFavorites(): Flow<List<MovieFavourite>?>
}
