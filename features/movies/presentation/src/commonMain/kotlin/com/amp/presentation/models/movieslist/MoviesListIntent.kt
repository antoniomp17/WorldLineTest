package com.amp.presentation.models.movieslist

sealed class MoviesListIntent {

    data class LoadMovies(
        val page: Int = 1,
        val refresh: Boolean = false
    ): MoviesListIntent()

    data class SelectMovie(val movieId: Int): MoviesListIntent()

    data object RefreshMovies: MoviesListIntent()

    data object LoadMoreMovies: MoviesListIntent()

}