package com.amp.presentation.components.moviedetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amp.entities.details.MovieDetails


@Composable
fun MovieDetailsInfo(
    movieDetails: MovieDetails,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Movie Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            InfoRow(
                label = "Status",
                value = movieDetails.status
            )

            if (movieDetails.budget > 0) {
                InfoRow(
                    label = "Budget",
                    value = "$${movieDetails.budget}"
                )
            }

            if (movieDetails.revenue > 0) {
                InfoRow(
                    label = "Revenue",
                    value = "${movieDetails.revenue}"
                )
            }

            if (movieDetails.originalTitle != movieDetails.title) {
                InfoRow(
                    label = "Original Title",
                    value = movieDetails.originalTitle
                )
            }

            InfoRow(
                label = "Original Language",
                value = movieDetails.originalLanguage.uppercase()
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}