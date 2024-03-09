package com.ds.movieapp.ui.screens.favourites

import com.ds.movieapp.ui.screens.common.viewmodel.Action

sealed class FavouritesAction : Action {
    data object NavigateBack : FavouritesAction()
}
