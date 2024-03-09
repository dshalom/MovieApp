package com.ds.movieapp.ui.screens.profile

import androidx.lifecycle.viewModelScope
import com.ds.movieapp.domain.repo.AuthenticationRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
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
                    authenticationRepo.createUser("abc@gmail.com", "123!@edeK")
                }

                ProfileEvent.OnLogOutClick -> {
                    authenticationRepo.logout()
                }
            }
        }
    }
}
