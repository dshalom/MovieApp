package com.ds.movieapp.domain.repo

import kotlinx.coroutines.flow.Flow

interface AuthenticationRepo {
    suspend fun login(email: String, password: String): Boolean
    suspend fun logout(): Unit
    suspend fun loggedIn(): Flow<Boolean>
}
