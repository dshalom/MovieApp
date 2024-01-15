package com.ds.movieapp.ui.screens.grid

import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.ui.screens.common.viewmodel.UiState

data class GridUiState(val movies: List<Movie>, val error: Boolean) :
    UiState
