package com.ds.movieapp.ui.screens.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.ui.screens.common.components.MovieUi

@Composable
fun GridUi(
    genreId: String,
    gridUiState: GridUiState,
    event: (GridEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        event(GridEvent.OnLoad(genreId))
    }
    Scaffold(
        modifier = Modifier.padding(horizontal = 8.dp),
        topBar = {
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                MoviesGrid(gridUiState.movies)
            }
        }
    }
}

@Composable
fun MoviesGrid(movies: List<Movie>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(movies) { movie ->

            MovieUi(movie)
        }
    }
}
