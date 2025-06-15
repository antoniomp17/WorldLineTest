package com.amp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amp.presentation.models.movieslist.MoviesListEffect
import com.amp.presentation.models.movieslist.MoviesListIntent
import com.amp.presentation.models.movieslist.MoviesListState
import com.amp.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesListState())
    val uiState: StateFlow<MoviesListState> = _uiState.asStateFlow()

    private val _effects = Channel<MoviesListEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()
    
    init {
        handleIntent(MoviesListIntent.LoadMoviesList())
    }

    fun handleIntent(intent: MoviesListIntent) {
        when (intent) {
            is MoviesListIntent.LoadMoviesList -> loadMovies(intent.page, intent.refresh)
            is MoviesListIntent.RefreshMoviesList -> refreshMovies()
            is MoviesListIntent.SelectMovie -> selectMovie(intent.movieId)
        }
    }
    
    private fun loadMovies(page: Int = 1, refresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isLoading = page == 1 && !refresh,
                    isRefreshing = refresh,
                    errorMessage = null
                )
                
                val result = getPopularMoviesUseCase(page = page)
                
                result.onSuccess { movies ->
                    _uiState.value = _uiState.value.copy(
                        movies = if (page == 1) movies else _uiState.value.movies + movies,
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = null,
                        currentPage = page,
                        hasMorePages = movies.size >= 20,
                        isEmpty = movies.isEmpty() && page == 1
                    )
                    
                    if (refresh && page == 1) {
                        sendEffect(MoviesListEffect.ScrollToTop)
                        sendEffect(MoviesListEffect.ShowSuccess("Movies refreshed"))
                    }
                }
                
                result.onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = exception.message ?: "Unknown error occurred"
                    )
                    sendEffect(MoviesListEffect.ShowError(exception.message ?: "Failed to load movies"))
                }
                
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = exception.message ?: "Unexpected error"
                )
                sendEffect(MoviesListEffect.ShowError("An unexpected error occurred"))
            }
        }
    }

    private fun selectMovie(movieId: Int) {
        sendEffect(MoviesListEffect.NavigateToDetails(movieId))
    }
    
    private fun refreshMovies() {
        loadMovies(page = 1, refresh = true)
    }

    private fun sendEffect(effect: MoviesListEffect) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }
}