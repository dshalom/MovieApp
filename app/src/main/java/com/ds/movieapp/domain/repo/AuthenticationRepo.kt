package com.ds.movieapp.domain.repo

import kotlinx.coroutines.flow.Flow

interface AuthenticationRepo {
    suspend fun createUser(email: String, password: String)
    suspend fun logout(): Unit
    suspend fun loggedIn(): Flow<Boolean>
}
