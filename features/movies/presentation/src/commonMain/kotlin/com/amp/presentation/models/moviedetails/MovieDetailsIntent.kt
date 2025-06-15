package com.amp.presentation.models.moviedetails

sealed class MovieDetailsIntent {

    data class LoadMovieDetails(
        val movieId: Int,
        val refresh: Boolean = false
    ) : MovieDetailsIntent()

    data object RefreshDetails : MovieDetailsIntent()

    data object NavigateBack : MovieDetailsIntent()
}