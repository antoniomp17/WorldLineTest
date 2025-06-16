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
        handleIntent(MoviesListIntent.LoadMovies())
    }

    fun handleIntent(intent: MoviesListIntent) {
        when (intent) {
            is MoviesListIntent.LoadMovies -> loadMovies(intent.page, intent.refresh)
            is MoviesListIntent.RefreshMovies -> refreshMovies()
            is MoviesListIntent.SelectMovie -> selectMovie(intent.movieId)
            is MoviesListIntent.LoadMoreMovies -> loadMoreMovies()
        }
    }

    private fun refreshMovies() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isRefreshing = true,
                    errorMessage = null
                )

                val result = getPopularMoviesUseCase(page = 1)

                result.onSuccess { movies ->
                    _uiState.value = _uiState.value.copy(
                        movies = movies,
                        isRefreshing = false,
                        errorMessage = null,
                        currentPage = 1,
                        hasMorePages = movies.size >= 20,
                        isEmpty = movies.isEmpty()
                    )

                    sendEffect(MoviesListEffect.ScrollToTop)
                    sendEffect(MoviesListEffect.ShowSuccess("Movies refreshed"))
                }

                result.onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isRefreshing = false,
                        errorMessage = exception.message ?: "Failed to refresh movies"
                    )
                    sendEffect(MoviesListEffect.ShowError("Failed to refresh movies"))
                }

            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    errorMessage = exception.message ?: "Unexpected error"
                )
                sendEffect(MoviesListEffect.ShowError("An unexpected error occurred"))
            }
        }
    }

    private fun loadMoreMovies() {
        val currentState = _uiState.value
        if (currentState.hasMorePages && !currentState.isLoading) {
            viewModelScope.launch {
                try {
                    _uiState.value = currentState.copy(isLoading = true)

                    val nextPage = currentState.currentPage + 1
                    val result = getPopularMoviesUseCase(page = nextPage)

                    result.onSuccess { newMovies ->
                        _uiState.value = _uiState.value.copy(
                            movies = _uiState.value.movies + newMovies,
                            isLoading = false,
                            currentPage = nextPage,
                            hasMorePages = newMovies.size >= 20,
                            errorMessage = null
                        )
                    }

                    result.onFailure { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = exception.message
                        )
                        sendEffect(MoviesListEffect.ShowError("Failed to load more movies"))
                    }

                } catch (exception: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                    sendEffect(MoviesListEffect.ShowError("Failed to load more movies"))
                }
            }
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
                        movies = if (page == 1) {
                            movies
                        } else {
                            _uiState.value.movies + movies
                        },
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
                        errorMessage = exception.message ?: "Failed to load movies"
                    )
                    sendEffect(MoviesListEffect.ShowError("Failed to load movies"))
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

    private fun sendEffect(effect: MoviesListEffect) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }
}