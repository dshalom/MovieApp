package com.ds.movieapp.ui.screens.profile

import androidx.lifecycle.viewModelScope
import com.ds.movieapp.domain.repo.ProfileRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepo: ProfileRepo
) : UdfViewModel<ProfileEvent, ProfileUiState, ProfileAction>(
    initialUiState = ProfileUiState(
        authorised = false,
        error = false
    )
) {

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
                ProfileEvent.OnAuthoriseClick -> {
                    profileRepo.authorise()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        profileRepo.onCleared()
    }
}
