package com.ds.movieapp.ui.screens.profile

import com.ds.movieapp.ui.screens.common.viewmodel.UiState

data class ProfileUiState(
    val loggedIn: Boolean,
    val requestToken: String,
    val error: Boolean
) : UiState
