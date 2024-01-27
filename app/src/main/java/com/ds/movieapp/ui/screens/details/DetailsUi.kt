package com.ds.movieapp.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ds.movieapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsUi(
    movieId: String,
    detailsUiState: DetailsUiState,
    navController: NavHostController,
    event: (DetailsEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        event(DetailsEvent.OnLoad(movieId))
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
        ElevatedCard(
            modifier = Modifier.padding(paddingValues),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(detailsUiState.movieDetails?.backdropPath)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),

                    contentDescription = null

                )
                Text(
                    text = detailsUiState.movieDetails?.title ?: "",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )

                Button(
                    onClick = {
                        event(
                            DetailsEvent.OnFavouriteClicked(
                                movieId,
                                !(detailsUiState.movieDetails?.isFavourite ?: false)
                            )
                        )
                    },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = if (detailsUiState.movieDetails?.isFavourite == true) {
                            "Remove favourite"
                        } else {
                            "Add to favourites"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
