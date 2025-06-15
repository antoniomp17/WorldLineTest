package com.amp.integration

import com.amp.di.dataModule
import com.amp.di.domainModule
import com.amp.usecases.GetPopularMoviesUseCase
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
import kotlin.test.assertTrue


class NetworkErrorIntegrationTest : KoinTest {

    @BeforeTest
    fun setup() {
        val networkErrorModule = module {
            single {
                HttpClient(MockEngine) {
                    engine {
                        addHandler { request ->
                            when {
                                request.url.toString().contains("timeout") -> {
                                    throw Exception("Network timeout")
                                }
                                request.url.toString().contains("unauthorized") -> {
                                    respond(
                                        content = """{"status_message":"Invalid API key"}""",
                                        status = HttpStatusCode.Unauthorized,
                                        headers = headersOf("Content-Type", "application/json")
                                    )
                                }
                                else -> {
                                    respond(
                                        content = """{"status_message":"Internal server error"}""",
                                        status = HttpStatusCode.InternalServerError,
                                        headers = headersOf("Content-Type", "application/json")
                                    )
                                }
                            }
                        }
                    }
                    install(ContentNegotiation) {
                        json(Json { ignoreUnknownKeys = true })
                    }
                }
            }
            single(qualifier = named("tmdb_api_key")) { "invalid_api_key" }
        }

        startKoin {
            modules(domainModule, dataModule, networkErrorModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Should handle server error`() = runTest {

        val getPopularMoviesUseCase: GetPopularMoviesUseCase by inject()

        val result = getPopularMoviesUseCase(page = 1)

        assertTrue(result.isFailure, "Server error should result in failure")
        
        println("Server error handled gracefully")
    }
}