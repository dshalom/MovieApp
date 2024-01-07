package com.ds.movieapp.domain.repo

interface ProfileRepo {

    suspend fun authorise(): Unit
    fun onCleared()
}
