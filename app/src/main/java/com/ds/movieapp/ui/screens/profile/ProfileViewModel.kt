package com.ds.movieapp.ui.screens.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ds.movieapp.domain.repo.AuthenticationRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticationRepo: AuthenticationRepo
) : UdfViewModel<ProfileEvent, ProfileUiState, ProfileAction>(
    initialUiState = ProfileUiState(
        loggedIn = false,
        requestToken = "",
        error = false
    )
) {

    init {
        viewModelScope.launch {
            authenticationRepo.loggedIn().collectLatest { loggedIn ->
                setUiState {
                    copy(
                        loggedIn = loggedIn
                    )
                }
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i("CoroutineExceptionHandler $throwable")
        setUiState {
            copy(
                error = true
            )
        }
    }

    override fun handleEvent(event: ProfileEvent) {
        viewModelScope.launch(exceptionHandler) {
            when (event) {
                ProfileEvent.OnLoginClick -> {
                    val res = authenticationRepo.login("abc@gmail.com", "123!@edeK")
                    setUiState {
                        copy(error = !res)
                    }
                }

                ProfileEvent.OnLogOutClick -> {
                    // authenticationRepo.logout()
                    val db = Firebase.firestore

                    val user = hashMapOf(
                        "first" to "Ada",
                        "last" to "Lovelace",
                        "born" to 1815
                    )

// Add a new document with a generated ID
                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d("dsds", "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("dsds", "Error adding document", e)
                        }
                }
            }
        }
    }
}
