package com.ds.movieapp.domain.repo

import kotlinx.coroutines.flow.Flow

interface AuthenticationRepo {
    suspend fun login(): Unit
    suspend fun logout(): Unit
    suspend fun loggedIn(): Flow<Boolean>
    fun onCleared()
}
