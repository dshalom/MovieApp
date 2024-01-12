package com.ds.movieapp.ui.screens.profile

import com.ds.movieapp.ui.screens.common.viewmodel.UiState

data class ProfileUiState(
    val authorised: Boolean,
    val requestToken: String,
    val error: Boolean
) : UiState