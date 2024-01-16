package com.ds.movieapp.ui.screens.details

import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.ui.screens.common.viewmodel.UiState

data class DetailsUiState(val movies: List<Movie>, val error: Boolean) :
    UiState
