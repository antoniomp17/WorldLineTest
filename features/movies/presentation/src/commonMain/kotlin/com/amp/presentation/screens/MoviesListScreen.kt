package com.amp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.amp.presentation.components.movieslist.MoviesListStates
import com.amp.presentation.components.movieslist.MoviesListContent
import com.amp.presentation.models.movieslist.MoviesListEffect
import com.amp.presentation.models.movieslist.MoviesListIntent
import com.amp.presentation.viewmodels.MoviesListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoviesListScreen(
    onNavigateToDetails: (Int) -> Unit = {},
    onShowMessage: (String) -> Unit = {},
    viewModel: MoviesListViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is MoviesListEffect.NavigateToDetails -> onNavigateToDetails(effect.movieId)
                is MoviesListEffect.ShowError -> onShowMessage(effect.message)
                is MoviesListEffect.ShowSuccess -> onShowMessage(effect.message)
                else -> {}
            }
        }
    }

    when {
        state.isLoading && state.movies.isEmpty() -> {
            MoviesListStates.LoadingScreen()
        }

        state.errorMessage != null || state.movies.isEmpty() -> {
            MoviesListStates.EmptyScreen(
                onRetry = {
                    viewModel.handleIntent(MoviesListIntent.LoadMovies(page = 1, refresh = true))
                }
            )
        }

        else -> {
            MoviesListContent(
                state = state,
                onMovieClick = { movieId ->
                    viewModel.handleIntent(MoviesListIntent.SelectMovie(movieId))
                },
                onRefresh = {
                    viewModel.handleIntent(MoviesListIntent.RefreshMovies)
                },
                onLoadMore = {
                    viewModel.handleIntent(MoviesListIntent.LoadMoreMovies)
                }
            )
        }
    }
}