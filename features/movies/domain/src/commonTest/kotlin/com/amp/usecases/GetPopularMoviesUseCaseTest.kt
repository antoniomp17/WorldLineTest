package com.amp.usecases

import com.amp.TestUtils
import com.amp.di.domainModule
import com.amp.repository.MoviesRepository
import dev.mokkery.answering.returns
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import dev.mokkery.everySuspend
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetMovieUseCaseTest : KoinTest {

    private val mockRepository = mock<MoviesRepository>()

    @BeforeTest
    fun setup() {
        val testModule = module {
            single<MoviesRepository> { mockRepository }
        }

        startKoin { modules(
            testModule,
            domainModule
        ) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `invoke should return movies from repository`() = runTest {
        val expectedMovies = listOf(TestUtils.createTestMovie())
        everySuspend {
            mockRepository.getPopularMovies(any(), any(), any())
        } returns Result.success(expectedMovies)

        val useCase: GetPopularMoviesUseCase by inject()

        val result = useCase(page = 1)

        assertTrue(result.isSuccess)
        val movies = result.getOrNull()
        assertNotNull(movies)
        assertEquals(1, movies.size)

        verifySuspend { mockRepository.getPopularMovies(1) }
    }

    @Test
    fun `invoke should filter invalid movies`() = runTest {
        val moviesWithInvalid = listOf(
            TestUtils.createTestMovie(id = 1, title = "Valid Movie"),
            TestUtils.createTestMovie(id = 0, title = ""),
            TestUtils.createTestMovie(id = 2, title = "Another Valid Movie")
        )
        everySuspend {
            mockRepository.getPopularMovies(any(), any(), any())
        } returns Result.success(moviesWithInvalid)

        val useCase: GetPopularMoviesUseCase by inject()

        val result = useCase(page = 1)

        assertTrue(result.isSuccess)
        val movies = result.getOrNull()
        assertNotNull(movies)
        assertEquals(2, movies.size)
    }

    @Test
    fun `invoke should handle repository error`() = runTest {
        val expectedException = Exception("Network error")
        everySuspend {
            mockRepository.getPopularMovies(any(), any(), any())
        } returns Result.failure(expectedException)

        val useCase: GetPopularMoviesUseCase by inject()

        val result = useCase(page = 1)

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
    }
}