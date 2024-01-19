package com.ds.movieapp.domain.repo

interface WatchListFavoritesRepo {

    suspend fun addToFavorites(): Unit
}
