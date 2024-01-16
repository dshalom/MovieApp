package com.ds.movieapp.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ds.movieapp.domain.models.Movie
import com.ds.movieapp.ui.screens.common.components.MovieUi
import com.ds.movieapp.ui.theme.MovieAppTheme

@Composable
fun MoviesUi(movies: List<Movie>, onMovieClicked: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            Box(
                modifier = Modifier.clickable {
                    onMovieClicked(movie.id.toString())
                }
            ) {
                MovieUi(movie)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesPreview() {
    MovieAppTheme {
        MoviesUi(
            listOf(
                Movie(
                    adult = false,
                    backdropPath = "",
                    genreIds = emptyList(),
                    id = 0,
                    overview = "",
                    popularity = 3.0,
                    posterPath = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
                    releaseDate = "",
                    title = "Aquaman and the Lost Kingdom",
                    voteAverage = 9.0
                )
            )
        ) {}
    }
}
