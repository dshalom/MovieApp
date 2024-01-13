package com.ds.movieapp.ui.screens.home

import com.ds.movieapp.ui.screens.common.viewmodel.Event

sealed class HomeEvent : Event {
    data object OnUpButtonClick : HomeEvent()
}
