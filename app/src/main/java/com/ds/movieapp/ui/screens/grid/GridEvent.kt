package com.ds.movieapp.ui.screens.grid

import com.ds.movieapp.ui.screens.common.viewmodel.Event

sealed class GridEvent : Event {
    data object OnUpButtonClicked : GridEvent()
    data class OnLoad(val genreId: String) : GridEvent()

    data class OnFavouriteClicked(val movieId: String, val isFavourite: Boolean) : GridEvent()
}
