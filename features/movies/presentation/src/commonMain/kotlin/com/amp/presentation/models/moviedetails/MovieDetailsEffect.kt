package com.amp.presentation.models.moviedetails

sealed class MovieDetailsEffect {

    data object NavigateBack : MovieDetailsEffect()

    data class ShowError(val message: String) : MovieDetailsEffect()

    data class ShowSuccess(val message: String) : MovieDetailsEffect()
}