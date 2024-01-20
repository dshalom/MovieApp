package com.ds.movieapp.ui.screens.grid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.ui.screens.Screen
import com.ds.movieapp.ui.screens.common.components.MovieUi

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
    Scaffold(modifier = Modifier.padding(horizontal = 8.dp), topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = "Details",
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
                modifier = Modifier.fillMaxSize()
            ) {
                MoviesGrid(gridUiState.movies) { movieId ->
                    navController.navigate("${Screen.DetailsScreen.route}/$movieId")
                }
            }
        }
    }
}

@Composable
fun MoviesGrid(movies: List<Movie>, onMovieClicked: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->

            Box(
                modifier = Modifier.clickable {
                    onMovieClicked(movie.id.toString())
                }
            ) {
                Column {
                    MovieUi(movie) { id, isFavourite ->
                    }
                }
            }
        }
    }
}
