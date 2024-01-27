package com.ds.movieapp.ui.screens.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ds.movieapp.domain.models.Movie

@Composable
fun MovieUi(movie: Movie, onFavouriteClicked: (String, Boolean) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(width = 200.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = null

            )
            Text(
                text = movie.title,
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )

            Button(onClick = {
                onFavouriteClicked(
                    movie.id,
                    !movie.isFavourite
                )
            }, modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = if (movie.isFavourite) {
                        "Remove favourite"
                    } else {
                        "Add to favourites"
                    },
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
