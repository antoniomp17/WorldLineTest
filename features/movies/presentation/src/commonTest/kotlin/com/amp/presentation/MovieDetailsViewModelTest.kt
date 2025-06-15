package com.amp.presentation


import com.amp.TestUtils
import com.amp.presentation.models.moviedetails.MovieDetailsEffect
import com.amp.presentation.models.moviedetails.MovieDetailsIntent
import com.amp.presentation.viewmodels.MovieDetailsViewModel
import com.amp.usecases.GetMovieDetailsUseCase
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    private lateinit var mockGetMovieDetailsUseCase: GetMovieDetailsUseCase

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockGetMovieDetailsUseCase = mock<GetMovieDetailsUseCase>()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be empty`() = runTest(testDispatcher) {
        val viewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)

        val initialState = viewModel.uiState.value
        assertNull(initialState.movieDetails)
        assertFalse(initialState.isLoading)
        assertNull(initialState.errorMessage)
        assertEquals(0, initialState.movieId)
        assertFalse(initialState.isRefreshing)
    }

    @Test
    fun `handleIntent LoadMovieDetails should update state to success`() = runTest(testDispatcher) {
        val movieId = 123
        val expectedDetails = TestUtils.createTestMovieDetails(id = movieId)

        everySuspend {
            mockGetMovieDetailsUseCase(movieId)
        } returns Result.success(expectedDetails)

        val viewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)

        viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(movieId))
        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(expectedDetails, finalState.movieDetails)
        assertFalse(finalState.isLoading)
        assertNull(finalState.errorMessage)
        assertEquals(movieId, finalState.movieId)
        assertFalse(finalState.isRefreshing)
    }

    @Test
    fun `handleIntent LoadMovieDetails should handle error`() = runTest(testDispatcher) {
        val movieId = 123
        val exception = Exception("Movie not found")

        everySuspend {
            mockGetMovieDetailsUseCase(movieId)
        } returns Result.failure(exception)

        val viewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)

        viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(movieId))
        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertNull(finalState.movieDetails)
        assertFalse(finalState.isLoading)
        assertEquals("Movie not found", finalState.errorMessage)
        assertEquals(movieId, finalState.movieId)
        assertFalse(finalState.isRefreshing)
    }

    @Test
    fun `handleIntent LoadMovieDetails should handle invalid movie id`() = runTest(testDispatcher) {
        val invalidMovieId = 0
        val viewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)

        viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(invalidMovieId))

        val effect = viewModel.effects.first()
        assertTrue(effect is MovieDetailsEffect.ShowError)
        assertTrue(effect.message.contains("Invalid movie ID"))
    }

    @Test
    fun `handleIntent RefreshDetails should reload current movie`() = runTest(testDispatcher) {
        val movieId = 123
        val initialDetails = TestUtils.createTestMovieDetails(id = movieId, title = "Initial Movie")
        val refreshedDetails = TestUtils.createTestMovieDetails(id = movieId, title = "Refreshed Movie")

        everySuspend {
            mockGetMovieDetailsUseCase(movieId)
        } returns Result.success(initialDetails)

        val viewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)

        viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(movieId))
        advanceUntilIdle()

        everySuspend {
            mockGetMovieDetailsUseCase(movieId)
        } returns Result.success(refreshedDetails)

        viewModel.handleIntent(MovieDetailsIntent.RefreshDetails)
        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(refreshedDetails, finalState.movieDetails)
        assertFalse(finalState.isRefreshing)
        assertEquals(movieId, finalState.movieId)
    }

    @Test
    fun `handleIntent NavigateBack should send navigation effect`() = runTest(testDispatcher) {
        val viewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)

        viewModel.handleIntent(MovieDetailsIntent.NavigateBack)

        val effect = viewModel.effects.first()
        assertTrue(effect is MovieDetailsEffect.NavigateBack)
    }

    @Test
    fun `handleIntent LoadMovieDetails with refresh should show success effect`() = runTest(testDispatcher) {
        val movieId = 123
        val movieDetails = TestUtils.createTestMovieDetails(id = movieId)

        everySuspend {
            mockGetMovieDetailsUseCase(movieId)
        } returns Result.success(movieDetails)

        val viewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)

        viewModel.handleIntent(MovieDetailsIntent.LoadMovieDetails(movieId, refresh = true))
        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(movieDetails, finalState.movieDetails)
        assertFalse(finalState.isRefreshing)

        val effect = viewModel.effects.first()
        assertTrue(effect is MovieDetailsEffect.ShowSuccess)
        assertEquals("Details refreshed", effect.message)
    }

    @Test
    fun `handleIntent RefreshDetails should not work if no movieId is set`() = runTest(testDispatcher) {
        val viewModel = MovieDetailsViewModel(mockGetMovieDetailsUseCase)

        viewModel.handleIntent(MovieDetailsIntent.RefreshDetails)
        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(0, finalState.movieId)
        assertNull(finalState.movieDetails)
        assertFalse(finalState.isLoading)
        assertFalse(finalState.isRefreshing)
    }
}