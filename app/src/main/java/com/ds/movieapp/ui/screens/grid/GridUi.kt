package com.ds.movieapp.ui.screens.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ds.movieapp.ui.screens.Screen
import com.ds.movieapp.ui.screens.common.components.MoviesGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridUi(
    genreId: String,
    genreTitle: String,
    gridUiState: GridUiState,
    navController: NavController,
    event: (GridEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        event(GridEvent.OnLoad(genreId))
    }
    Scaffold(modifier = Modifier.padding(horizontal = 8.dp), topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = genreTitle,
                style = MaterialTheme.typography.titleMedium
            )
        }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
}
