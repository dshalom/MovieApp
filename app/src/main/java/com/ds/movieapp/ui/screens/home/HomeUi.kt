package com.ds.movieapp.ui.screens.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ds.movieapp.data.models.Genre
import com.ds.movieapp.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUi(
    homeUiState: HomeUiState,
    navController: NavHostController,
    event: (HomeEvent) -> Unit,
    error: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        item {
            GenreChips(
                modifier = Modifier.height(50.dp),
                titles = homeUiState.genres,
                selectedId = homeUiState.selectedGenreId
            ) { id ->
                event(HomeEvent.OnGenreClicked(id))
            }
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Popular",
                    style = MaterialTheme.typography.titleLarge
                )
                Button(
                    onClick = {
                        navController.navigate("${Screen.GridScreen.route}/${homeUiState.selectedGenreId}")
                    },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "See all",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        item {
            MoviesUi(
                movies = homeUiState.movies,
                onMovieClicked = { id ->
                    navController.navigate("${Screen.DetailsScreen.route}/$id")
                },
                onFavouriteClicked = { id, isFavourite ->
                    event(HomeEvent.OnFavouriteClicked(id, isFavourite))
                }
            )
        }
    }
    if (homeUiState.error) {
        error("Error")
    }
}

@Composable
fun GenreChips(
    titles: List<Genre>,
    selectedId: String,
    modifier: Modifier = Modifier,
    onGenreClicked: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
    ) {
        titles.forEach { genre ->
            GenreChip(genre.name, genre.id == selectedId) {
                onGenreClicked(genre.id)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreChip(title: String, selected: Boolean, onClicked: () -> Unit) {
    ElevatedFilterChip(selected = selected, onClick = {
        onClicked()
    }, label = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        })
}
