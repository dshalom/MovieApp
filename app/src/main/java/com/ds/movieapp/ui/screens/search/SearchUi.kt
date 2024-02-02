package com.ds.movieapp.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ds.movieapp.ui.screens.common.components.MovieUi

@Composable
fun SearchUi(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.padding(horizontal = 8.dp),
        topBar = {
            DoingSearch(searchViewModel)
        }

    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DoingSearch(searchViewModel: SearchViewModel) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val movies by searchViewModel.movies.collectAsState()

    SearchBar(
        query = query,
        onQueryChange = {
            query = it
            searchViewModel.onSearchTextChanged(it)
        },
        onSearch = {
        },
        active = active,
        onActiveChange = { active = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
        }

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(movies) { movie ->

                MovieUi(movie) { id, isFavourite ->
                }
            }
        }
    }
}
