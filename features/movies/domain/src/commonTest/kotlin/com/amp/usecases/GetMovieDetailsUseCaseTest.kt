package com.amp.usecases

import com.amp.TestUtils
import com.amp.di.domainModule
import com.amp.repository.MoviesRepository
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.*

class GetMovieDetailsUseCaseTest : KoinTest {

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
    fun `invoke should return movie details from repository`() = runTest {
        val movieId = 123
        val expectedMovieDetails = TestUtils.createTestMovieDetails(id = movieId)
        everySuspend {
            mockRepository.getMovieDetails(movieId, any())
        } returns Result.success(expectedMovieDetails)

        val useCase: GetMovieDetailsUseCase by inject()

        val result = useCase(movieId)

        assertTrue(result.isSuccess)
        val movieDetails = result.getOrNull()
        assertNotNull(movieDetails)
        assertEquals(movieId, movieDetails.id)
        assertEquals("Test Movie Details", movieDetails.title)

        verifySuspend { mockRepository.getMovieDetails(movieId, "en-US") }
    }

    @Test
    fun `invoke should return error for invalid movie id zero`() = runTest {
        val invalidMovieId = 0

        val useCase: GetMovieDetailsUseCase by inject()

        val result = useCase(invalidMovieId)

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)

        verifySuspend(mode = VerifyMode.not) { mockRepository.getMovieDetails(any(), any()) }
    }

    @Test
    fun `invoke should return error for negative movie id`() = runTest {

        val negativeMovieId = -5

        val useCase: GetMovieDetailsUseCase by inject()

        val result = useCase(negativeMovieId)

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        verifySuspend(mode = VerifyMode.not) { mockRepository.getMovieDetails(any(), any()) }
    }

    @Test
    fun `invoke should handle repository error`() = runTest {

        val movieId = 123
        val expectedException = Exception("Movie not found")
        everySuspend {
            mockRepository.getMovieDetails(movieId, any())
        } returns Result.failure(expectedException)

        val useCase: GetMovieDetailsUseCase by inject()

        val result = useCase(movieId)

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)

        verifySuspend { mockRepository.getMovieDetails(movieId, "en-US") }
    }

    @Test
    fun `invoke should handle mismatched movie id in response`() = runTest {

        val requestedMovieId = 123
        val wrongMovieDetails = TestUtils.createTestMovieDetails(id = 456)
        everySuspend {
            mockRepository.getMovieDetails(requestedMovieId, any())
        } returns Result.success(wrongMovieDetails)

        val useCase: GetMovieDetailsUseCase by inject()

        val result = useCase(requestedMovieId)

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception.message?.contains("Film ID mismatch") == true)

        verifySuspend { mockRepository.getMovieDetails(requestedMovieId, "en-US") }
    }

    @Test
    fun `invoke should work with custom language parameter`() = runTest {
        val movieId = 123
        val language = "es-ES"
        val expectedMovieDetails = TestUtils.createTestMovieDetails(id = movieId)
        everySuspend {
            mockRepository.getMovieDetails(movieId, language)
        } returns Result.success(expectedMovieDetails)

        val useCase: GetMovieDetailsUseCase by inject()

        val result = useCase(movieId, language)

        assertTrue(result.isSuccess)
        val movieDetails = result.getOrNull()
        assertNotNull(movieDetails)
        assertEquals(movieId, movieDetails.id)

        verifySuspend { mockRepository.getMovieDetails(movieId, language) }
    }

    @Test
    fun `invoke should handle network timeout exception`() = runTest {

        val movieId = 123
        everySuspend {
            mockRepository.getMovieDetails(movieId, any())
        } throws Exception("Network timeout")

        val useCase: GetMovieDetailsUseCase by inject()

        val result = useCase(movieId)

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
    }
}
