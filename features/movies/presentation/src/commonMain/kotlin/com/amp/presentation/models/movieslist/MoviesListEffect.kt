package com.amp.presentation.models.movieslist

sealed class MoviesListEffect {

    data class NavigateToDetails(val movieId: Int): MoviesListEffect()

    data class ShowError(val message: String): MoviesListEffect()

    data class ShowSuccess(val message: String): MoviesListEffect()

    data object ScrollToTop: MoviesListEffect()

}