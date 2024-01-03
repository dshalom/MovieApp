package com.ds.movieapp.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeContent(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Text(text = "HomeContent")
}
