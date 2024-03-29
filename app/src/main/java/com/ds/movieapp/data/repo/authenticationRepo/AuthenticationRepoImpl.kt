package com.ds.movieapp.data.repo.authenticationRepo

import com.ds.movieapp.domain.repo.AuthenticationRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepoImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthenticationRepo {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    override suspend fun createUser(email: String, password: String) {
        runCatching {
            auth.createUserWithEmailAndPassword(email, password).await()
        }.onFailure {
            if (it is FirebaseAuthUserCollisionException) {
                logIn(email, password)
            } else {
                throw it
            }
        }
    }

    private suspend fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun loggedIn(): Flow<Boolean> {
        return callbackFlow {
            val authStateListener = FirebaseAuth.AuthStateListener { auth ->
                trySend(auth.currentUser != null)
            }

            auth.addAuthStateListener(authStateListener)
            awaitClose {
                auth.removeAuthStateListener(authStateListener)
            }
        }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), auth.currentUser != null)
    }
}
