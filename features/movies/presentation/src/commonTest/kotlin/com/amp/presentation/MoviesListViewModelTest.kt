package com.amp.presentation

import com.amp.TestUtils
import com.amp.presentation.models.movieslist.MoviesListEffect
import com.amp.presentation.models.movieslist.MoviesListIntent
import com.amp.presentation.viewmodels.MoviesListViewModel
import com.amp.usecases.GetPopularMoviesUseCase
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
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
class MoviesListViewModelTest {

    private lateinit var mockGetPopularMoviesUseCase: GetPopularMoviesUseCase

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockGetPopularMoviesUseCase = mock<GetPopularMoviesUseCase>()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be loading`() = runTest(testDispatcher) {

        everySuspend {
            mockGetPopularMoviesUseCase(page = any())
        } returns Result.success(emptyList())

        val viewModel = MoviesListViewModel(mockGetPopularMoviesUseCase)

        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(emptyList(), finalState.movies)
        assertFalse(finalState.isLoading)
        assertNull(finalState.errorMessage)
        assertTrue(finalState.isEmpty)
        assertFalse(finalState.hasMorePages)
    }

    @Test
    fun `handleIntent LoadMovies should update state to success`() = runTest(testDispatcher) {

        everySuspend {
            mockGetPopularMoviesUseCase(page = any())
        } returns Result.success(emptyList())

        val viewModel = MoviesListViewModel(mockGetPopularMoviesUseCase)
        advanceUntilIdle()

        val expectedMovies = listOf(TestUtils.createTestMovie())
        everySuspend {
            mockGetPopularMoviesUseCase(page = 1)
        } returns Result.success(expectedMovies)

        viewModel.handleIntent(MoviesListIntent.LoadMoviesList(page = 1))
        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(expectedMovies, finalState.movies)
        assertFalse(finalState.isLoading)
        assertNull(finalState.errorMessage)
        assertEquals(1, finalState.currentPage)
    }

    @Test
    fun `handleIntent SelectMovie should send navigation effect`() = runTest(testDispatcher) {
        everySuspend { mockGetPopularMoviesUseCase(page = any()) } returns Result.success(emptyList())
        val viewModel = MoviesListViewModel(mockGetPopularMoviesUseCase)
        advanceUntilIdle()

        val movieId = 123

        viewModel.handleIntent(MoviesListIntent.SelectMovie(movieId))

        val effect = viewModel.effects.first()
        assertTrue(effect is MoviesListEffect.NavigateToDetails)
        assertEquals(movieId, effect.movieId)
    }

    @Test
    fun `handleIntent RefreshMovies should reload movies from page 1`() = runTest(testDispatcher) {
        val initialMovies = listOf(TestUtils.createTestMovie(id = 1))
        val refreshedMovies = listOf(
            TestUtils.createTestMovie(id = 2, title = "Refreshed Movie 1"),
            TestUtils.createTestMovie(id = 3, title = "Refreshed Movie 2")
        )

        everySuspend { mockGetPopularMoviesUseCase(page = any()) } returns Result.success(initialMovies)

        val viewModel = MoviesListViewModel(mockGetPopularMoviesUseCase)
        advanceUntilIdle()

        everySuspend { mockGetPopularMoviesUseCase(page = 1) } returns Result.success(refreshedMovies)

        viewModel.handleIntent(MoviesListIntent.RefreshMoviesList)
        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(refreshedMovies, finalState.movies)
        assertFalse(finalState.isRefreshing)
        assertEquals(1, finalState.currentPage)
    }
}