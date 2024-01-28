package com.ds.movieapp.ui.screens.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ds.movieapp.domain.models.Movie

@Composable
fun MoviesGrid(
    movies: List<Movie>,
    onMovieClicked: (String) -> Unit,
    onFavouriteClicked: (String, Boolean) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(movies) { movie ->

            Box(
                modifier = Modifier.clickable {
                    onMovieClicked(movie.id)
                }
            ) {
                Column {
                    MovieUi(movie) { id, isFavourite ->
                        onFavouriteClicked(id, isFavourite)
                    }
                }
            }
        }
    }
}
