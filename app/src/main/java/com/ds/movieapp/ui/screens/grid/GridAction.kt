package com.ds.movieapp.ui.screens.grid

import com.ds.movieapp.ui.screens.common.viewmodel.Action

sealed class GridAction : Action {
    data object NavigateBack : GridAction()
}
