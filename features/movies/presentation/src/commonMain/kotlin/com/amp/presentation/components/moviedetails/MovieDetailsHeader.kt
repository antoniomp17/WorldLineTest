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
            MoviePoster(movieDetails)
            MovieInfo(movieDetails)
        }
    }
}

@Composable
private fun MoviePoster(movieDetails: MovieDetails) {
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
}

@Composable
private fun MovieInfo(movieDetails: MovieDetails) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MovieTitle(movieDetails.title, movieDetails.tagline)
            MovieMetadata(movieDetails)
        }
    }
}

@Composable
private fun MovieTitle(title: String, tagline: String?) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
    
    if (tagline?.isNotBlank() == true) {
        Text(
            text = tagline,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MovieMetadata(movieDetails: MovieDetails) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingDisplay(movieDetails.voteAverage)
        ReleaseDateDisplay(movieDetails.releaseDate)
    }
    
    movieDetails.runtime.toString().let { runtime ->
        Text(
            text = runtime,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun RatingDisplay(voteAverage: Double) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "⭐")
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = voteAverage.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ReleaseDateDisplay(releaseDate: String?) {
    Text(
        text = releaseDate ?: "",
        style = MaterialTheme.typography.bodyMedium
    )
}