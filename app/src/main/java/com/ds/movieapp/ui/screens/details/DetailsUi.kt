package com.ds.movieapp.ui.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ds.movieapp.R
import com.ds.movieapp.domain.models.MovieDetails
import com.ds.movieapp.ui.screens.common.components.GenreChips
import com.ds.movieapp.ui.theme.MovieAppTheme

private const val IMAGE_WIDTH = 16f
private const val IMAGE_HEIGHT = 9f

@Composable
fun DetailsUi(
    movieId: String,
    detailsUiState: DetailsUiState,
    event: (DetailsEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        event(DetailsEvent.OnLoad(movieId))
    }

    ElevatedCard(
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(detailsUiState.movieDetails?.backdropPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(IMAGE_WIDTH / IMAGE_HEIGHT)
            )

            val storesList: String? = ""

            storesList?.takeIf { it.isNotEmpty() }?.apply {
                // Provides you list if not empty
            }

            var isExpanded by remember {
                mutableStateOf(false)
            }
            val transition = updateTransition(targetState = isExpanded, label = "expanded")
            val iconRotation by transition.animateFloat(label = "iconRotation") { isExpanded ->
                if (isExpanded) 180f else 0f
            }

            detailsUiState.movieDetails?.title?.takeIf { it.isNotEmpty() }?.let {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            isExpanded = !isExpanded
                        }
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .rotate(iconRotation)
                    )
                }
                detailsUiState.movieDetails.tagline.takeIf { it.isNotEmpty() }?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                detailsUiState.movieDetails.overview.let {
                    AnimatedVisibility(visible = isExpanded) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            detailsUiState.movieDetails?.voteAverage.toString().let {
                Text(
                    text = it,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                detailsUiState.movieDetails?.cast?.forEach {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it.profilePath)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.placeholder),
                            contentDescription = null
                        )

                        Text(
                            text = it.name,
                            modifier = Modifier.padding(vertical = 8.dp),
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            detailsUiState.movieDetails?.director?.let {
                Text(
                    text = "Director: $it",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            GenreChips(
                modifier = Modifier.padding(vertical = 8.dp),
                titles = detailsUiState.movieDetails?.genres ?: emptyList()
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

@Preview(
    device = "id:pixel_5",
    showBackground = true
)
@Composable
fun DetailsUiPreview() {
    MovieAppTheme {
        DetailsUi(
            movieId = "",
            detailsUiState = DetailsUiState(
                movieDetails = MovieDetails(
                    id = "",
                    title = "Badland Hunters",
                    director = "Steven Spilberg",
                    tagline = "One last hunt to save us all.",
                    voteAverage = 6.5f,
                    overview = "After a deadly earthquake turns Seoul into a lawless badland, a fearless huntsman springs into action to rescue a teenager abducted by a mad doctor.",
                    genres = listOf("Action", "Science Fiction", "Drama"),
                    backdropPath = "https://picsum.photos/300/300",
                    isFavourite = true,
                    cast = emptyList()
                ),
                error = false
            )
        ) {
        }
    }
}
