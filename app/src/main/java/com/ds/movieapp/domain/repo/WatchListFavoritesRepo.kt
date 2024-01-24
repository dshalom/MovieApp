package com.ds.movieapp.domain.repo

import kotlinx.coroutines.flow.Flow

data class MovieFavourite(val movieId: String)

interface WatchListFavoritesRepo {

    fun addToFavorites(movieId: Int)
    fun removeFromFavorites(movieId: Int)
    fun observeFavorites(): Flow<List<MovieFavourite>?>
}
