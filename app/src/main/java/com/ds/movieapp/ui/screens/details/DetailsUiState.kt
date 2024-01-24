package com.ds.movieapp.ui.screens.details

import com.ds.movieapp.domain.models.MovieDetails
import com.ds.movieapp.ui.screens.common.viewmodel.UiState

data class DetailsUiState(val movieDetails: MovieDetails?, val error: Boolean) :
    UiState
