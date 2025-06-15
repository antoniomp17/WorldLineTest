package com.amp.integration

import com.amp.di.dataModule
import com.amp.di.domainModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class EndToEndIntegrationTest: KoinTest {

    @BeforeTest
    fun setup() {
        val testNetworkModule = module {
            single {
                HttpClient(MockEngine) {
                    engine {
                        addHandler { request ->
                            val urlString = request.url.toString()
                            when {
                                urlString.startsWith("https://api.themoviedb.org/3/movie/popular") -> {
                                    respond(
                                        content = mockTMDBResponse,
                                        status = HttpStatusCode.OK,
                                        headers = headersOf("Content-Type", "application/json")
                                    )
                                }
                                else -> error("Unhandled $urlString")
                            }
                        }
                    }
                    install(ContentNegotiation) {
                        json(Json { ignoreUnknownKeys = true })
                    }
                }
            }
            single(qualifier = named("tmdb_api_key")) { "test_api_key" }
        }

        startKoin {
            modules(
                domainModule,
                dataModule,
                testNetworkModule
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Use Cases should work with real Repository implementation`() = runTest {

        val getPopularMoviesUseCase: GetPopularMoviesUseCase by inject()

        val result = getPopularMoviesUseCase(page = 1)

        assertTrue(result.isSuccess)
        val movies = result.getOrNull()
        assertNotNull(movies)
        assertTrue(movies.isNotEmpty())
        println("Integration test passed: ${movies.size} movies loaded")
    }

    private val mockTMDBResponse = """
    {
        "page": 1,
        "results": [
            {
                "adult": false,
                "backdrop_path": "/backdrop.jpg",
                "genre_ids": [28, 12],
                "id": 550,
                "original_language": "en", 
                "original_title": "Test Movie",
                "overview": "Test overview",
                "popularity": 100.0,
                "poster_path": "/poster.jpg",
                "release_date": "2024-01-01",
                "title": "Test Movie",
                "video": false,
                "vote_average": 8.0,
                "vote_count": 1000
            }
        ],
        "total_pages": 100,
        "total_results": 2000
    }
    """.trimIndent()
}