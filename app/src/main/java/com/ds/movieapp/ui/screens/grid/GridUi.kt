package com.ds.movieapp.ui.screens.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ds.movieapp.ui.screens.Screen
import com.ds.movieapp.ui.screens.common.components.MoviesGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridUi(
    genreId: String,
    gridUiState: GridUiState,
    navController: NavController,
    event: (GridEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        event(GridEvent.OnLoad(genreId))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            MoviesGrid(
                gridUiState.movies,
                onMovieClicked = { movieId ->
                    navController.navigate("${Screen.DetailsScreen.route}/$movieId")
                },
                onFavouriteClicked = { id, isFavorite ->
                    event(GridEvent.OnFavouriteClicked(id, isFavorite))
                }
            )
        }
    }
}
