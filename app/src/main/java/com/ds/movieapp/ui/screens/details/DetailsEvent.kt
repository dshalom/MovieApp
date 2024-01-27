package com.ds.movieapp.ui.screens.details

import com.ds.movieapp.ui.screens.common.viewmodel.Event

sealed class DetailsEvent : Event {
    data object OnUpButtonClicked : DetailsEvent()
    data class OnLoad(val movieId: String) : DetailsEvent()
    data class OnFavouriteClicked(val movieId: String, val isFavourite: Boolean) : DetailsEvent()
}
