package com.ds.movieapp.ui.screens.home

import com.ds.movieapp.data.models.Genre
import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.ui.screens.common.viewmodel.UiState

data class HomeUiState(
    val genres: List<Genre>,
    val movies: List<Movie>,
    val error: Boolean,
    var selectedGenre: Int = 0
) :
    UiState
