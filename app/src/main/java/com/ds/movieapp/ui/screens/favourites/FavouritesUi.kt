package com.ds.movieapp.ui.screens.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FavouritesUi(
    favouritesUiState: FavouritesUiState,
    event: (FavouritesEvent) -> Unit,
    error: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Favourites",
            style = MaterialTheme.typography.headlineLarge
        )
    }
    if (favouritesUiState.error) {
        error("Error")
    }
}
