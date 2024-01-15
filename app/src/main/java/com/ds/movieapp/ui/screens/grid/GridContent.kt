package com.ds.movieapp.ui.screens.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GridContent(
    genreId: String,
    gridUiState: GridUiState,
    event: (GridEvent) -> Unit
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
                    Text(text = "grid  $genreId")
                }
            }
        }

        event(GridEvent.OnUpButtonClicked)
    }
}
