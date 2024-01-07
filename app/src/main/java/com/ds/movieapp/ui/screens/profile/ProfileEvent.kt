package com.ds.movieapp.ui.screens.profile

import com.ds.movieapp.ui.screens.common.viewmodel.Event

sealed class ProfileEvent : Event {
    data object OnAuthoriseClick : ProfileEvent()
}
