package com.amp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amp.presentation.models.moviedetails.MovieDetailsEffect
import com.amp.presentation.models.moviedetails.MovieDetailsIntent
import com.amp.presentation.models.moviedetails.MovieDetailsState
import com.amp.usecases.GetMovieDetailsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsState())
    val uiState: StateFlow<MovieDetailsState> = _uiState.asStateFlow()

    private val _effects = Channel<MovieDetailsEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun handleIntent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.LoadMovieDetails -> loadMovieDetails(intent.movieId, intent.refresh)
            is MovieDetailsIntent.RefreshDetails -> refreshDetails()
            is MovieDetailsIntent.NavigateBack -> navigateBack()
        }
    }
    
    private fun loadMovieDetails(movieId: Int, refresh: Boolean = false) {
        if (movieId <= 0) {
            sendEffect(MovieDetailsEffect.ShowError("Invalid movie ID"))
            return
        }
        
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    movieId = movieId,
                    isLoading = !refresh,
                    isRefreshing = refresh,
                    errorMessage = null
                )
                
                val result = getMovieDetailsUseCase(movieId)
                
                result.onSuccess { movieDetails ->
                    _uiState.value = _uiState.value.copy(
                        movieDetails = movieDetails,
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = null
                    )
                    
                    if (refresh) {
                        sendEffect(MovieDetailsEffect.ShowSuccess("Details refreshed"))
                    }
                }
                
                result.onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = exception.message ?: "Failed to load movie details"
                    )
                    sendEffect(MovieDetailsEffect.ShowError(
                        exception.message ?: "Failed to load movie details"
                    ))
                }
                
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = exception.message ?: "Unexpected error"
                )
                sendEffect(MovieDetailsEffect.ShowError("An unexpected error occurred"))
            }
        }
    }
    
    private fun refreshDetails() {
        val currentState = _uiState.value
        if (currentState.movieId > 0) {
            loadMovieDetails(currentState.movieId, refresh = true)
        }
    }
    
    private fun navigateBack() {
        sendEffect(MovieDetailsEffect.NavigateBack)
    }

    private fun sendEffect(effect: MovieDetailsEffect) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }
}