package com.ds.movieapp.ui.screens.favourites

import com.ds.movieapp.domain.repo.AuthenticationRepo
import com.ds.movieapp.ui.screens.common.viewmodel.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val authenticationRepo: AuthenticationRepo
) : UdfViewModel<FavouritesEvent, FavouritesUiState, FavouritesAction>(
    initialUiState = FavouritesUiState(
        loggedIn = false,
        requestToken = "",
        error = false
    )
) {

    override fun handleEvent(event: FavouritesEvent) {
    }
}
