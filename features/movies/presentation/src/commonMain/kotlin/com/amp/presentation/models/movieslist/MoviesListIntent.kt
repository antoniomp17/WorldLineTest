package com.amp.presentation.models.movieslist

sealed class MoviesListIntent {

    data class LoadMoviesList(
        val page: Int = 1,
        val refresh: Boolean = false
    ): MoviesListIntent()

    data class SelectMovie(val movieId: Int): MoviesListIntent()

    data object RefreshMoviesList: MoviesListIntent()
}