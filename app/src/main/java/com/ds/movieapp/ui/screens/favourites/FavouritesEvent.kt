package com.ds.movieapp.ui.screens.favourites

import com.ds.movieapp.ui.screens.common.viewmodel.Event

sealed class FavouritesEvent : Event {
    data object OnLoginClick : FavouritesEvent()
    data object OnLogOutClick : FavouritesEvent()
}
