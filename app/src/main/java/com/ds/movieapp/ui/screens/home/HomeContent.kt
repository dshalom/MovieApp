package com.ds.movieapp.ui.screens.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ds.movieapp.data.models.Genre
import com.ds.movieapp.data.models.MoviesDto

@Composable
fun HomeContent(
    homeUiState: HomeUiState,
    event: (HomeEvent) -> Unit
) {
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tasks",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                item {
                    GenreChips(titles = homeUiState.genres) {
                        event(HomeEvent.OnGenreClicked(it))
                    }
                }
                item {
                    Movies(moviesDto = homeUiState.movies)
                }
            }
        }

        event(HomeEvent.OnUpButtonClicked)
    }
}

@Composable
fun Movies(moviesDto: MoviesDto) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(moviesDto.results) { movie ->
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun GenreChips(
    titles: List<Genre>,
    modifier: Modifier = Modifier,
    onGenreClicked: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        titles.forEachIndexed { index, genre ->
            GenreChip(genre.name, selectedIndex == index) {
                selectedIndex = index
                onGenreClicked(genre.id.toString())
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
