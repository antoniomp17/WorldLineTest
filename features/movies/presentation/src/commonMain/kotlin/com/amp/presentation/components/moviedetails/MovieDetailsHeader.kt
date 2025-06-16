package com.amp.presentation.components.moviedetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.amp.entities.details.MovieDetails
import com.amp.presentation.utils.ImageUtils


@Composable
fun MovieDetailsHeader(
    movieDetails: MovieDetails,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Card(
                modifier = Modifier.size(width = 120.dp, height = 180.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                AsyncImage(
                    model = ImageUtils.buildPosterUrl(
                        posterPath = movieDetails.posterPath,
                        size = ImageUtils.PosterSize.W342
                    ),
                    contentDescription = "Poster for ${movieDetails.title}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = movieDetails.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                if (movieDetails.tagline?.isNotBlank() == true) {
                    Text(
                        text = movieDetails.tagline?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⭐")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = movieDetails.voteAverage.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = movieDetails.releaseDate ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                movieDetails.runtime.toString().let { runtime ->
                    Text(
                        text = runtime,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}