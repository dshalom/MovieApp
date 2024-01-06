package com.ds.movieapp.ui.screens.home

import com.ds.movieapp.ui.screens.common.viewmodel.Action

sealed class HomeAction : Action {
    data object NavigateBack : HomeAction()
}
