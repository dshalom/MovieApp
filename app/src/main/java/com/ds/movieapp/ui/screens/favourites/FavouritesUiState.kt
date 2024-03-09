package com.ds.movieapp.ui.screens.favourites

import com.ds.movieapp.ui.screens.common.viewmodel.UiState

data class FavouritesUiState(
    val loggedIn: Boolean,
    val requestToken: String,
    val error: Boolean
) : UiState
