package com.ds.movieapp.ui.screens.home

import com.ds.movieapp.ui.screens.common.viewmodel.Event

sealed class HomeEvent : Event {
    data object OnUpButtonClicked : HomeEvent()
    data class OnGenreClicked(val genreId: Int) : HomeEvent()
    data class OnSeeAllClicked(val genreId: Int) : HomeEvent()
}
