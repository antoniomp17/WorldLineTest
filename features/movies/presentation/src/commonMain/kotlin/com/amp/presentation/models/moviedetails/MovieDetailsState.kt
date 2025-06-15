package com.amp.presentation.models.moviedetails

import com.amp.entities.details.MovieDetails

data class MovieDetailsState(
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val movieId: Int = 0
) {

    val shouldShowContent: Boolean
        get() = !isLoading && movieDetails != null && errorMessage == null

    val shouldShowError: Boolean
        get() = errorMessage != null && !isLoading

    val shouldShowLoading: Boolean
        get() = isLoading && movieDetails == null

    val safeTitle: String
        get() = movieDetails?.title ?: "Movie Details"

    val hasValidData: Boolean
        get() = movieDetails != null && movieDetails.id > 0

}