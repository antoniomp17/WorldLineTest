package com.amp.presentation.screens

import androidx.compose.runtime.*
import com.amp.presentation.components.moviedetails.MovieDetailsContent
import com.amp.presentation.components.moviedetails.MovieDetailsStates
import com.amp.presentation.models.moviedetails.MovieDetailsEffect
import com.amp.presentation.models.moviedetails.MovieDetailsIntent
import com.amp.presentation.viewmodels.MovieDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onNavigateBack: () -> Unit = {},
    onShowMessage: (String) -> Unit = {},
    viewModel: MovieDetailsViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(movieId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is MovieDetailsEffect.NavigateBack -> onNavigateBack()
                is MovieDetailsEffect.ShowError -> onShowMessage(effect.message)
                is MovieDetailsEffect.ShowSuccess -> onShowMessage(effect.message)
            }
        }
    }

    when {
        state.isLoading && state.movieDetails == null -> {
            MovieDetailsStates.LoadingScreen()
        }

        state.errorMessage != null && state.movieDetails == null -> {
            MovieDetailsStates.ErrorScreen(
                message = state.errorMessage ?: "Unknown error",
                onRetry = { 
                    viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(movieId))
                },
                onNavigateBack = {
                    viewModel.handleIntent(MovieDetailsIntent.NavigateBack)
                }
            )
        }

        state.movieDetails != null -> {
            MovieDetailsContent(
                state = state,
                onNavigateBack = {
                    viewModel.handleIntent(MovieDetailsIntent.NavigateBack)
                },
                onRefresh = {
                    viewModel.handleIntent(MovieDetailsIntent.RefreshDetails)
                }
            )
        }
    }
}