package com.ds.movieapp.ui.screens.details

import com.ds.movieapp.ui.screens.common.viewmodel.Action

sealed class DetailsAction : Action {
    data object NavigateBack : DetailsAction()
}
