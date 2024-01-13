package com.ds.movieapp.ui.screens.profile

import com.ds.movieapp.ui.screens.common.viewmodel.Action

sealed class ProfileAction : Action {
    data object NavigateBack : ProfileAction()
}
