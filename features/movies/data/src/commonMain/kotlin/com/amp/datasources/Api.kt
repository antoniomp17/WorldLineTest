package com.amp.datasources

import com.amp.data.BuildKonfig
import com.amp.models.PopularMovieResponse
import com.amp.entities.details.MovieDetails
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

class Api(
    private val httpClient: HttpClient,
    private val apiKey: String = getApiKey()
) {
    
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3"
        private const val POPULAR_MOVIES_ENDPOINT = "$BASE_URL/movie/popular"
        private const val MOVIE_DETAILS_ENDPOINT = "$BASE_URL/movie"
        

        private fun getApiKey(): String {
            return BuildKonfig.API_KEY
        }
    }

    suspend fun getPopularMovies(
        page: Int = 1,
        language: String = "en-US"
    ): Result<PopularMovieResponse> {
        return try {
            Result.success(
                httpClient.get(POPULAR_MOVIES_ENDPOINT) {
                    header("Authorization", "Bearer $apiKey")
                    header("accept", "application/json")
                    parameter("language", language)
                    parameter("page", page)
                }.body<PopularMovieResponse>())
            
        } catch (exception: Exception) {
            Result.failure(
                Exception("Error retrieving popular movies: ${exception.message}", exception)
            )
        }
    }

    suspend fun getMovieDetails(
        movieId: Int,
        language: String = "en-US"
    ): MovieDetails {
        return try {
            httpClient.get("$MOVIE_DETAILS_ENDPOINT/$movieId") {
                header("Authorization", "Bearer $apiKey")
                header("accept", "application/json")

                parameter("language", language)
            }.body<MovieDetails>()

        } catch (exception: Exception) {
            throw Exception(
                "Error retrieving movie details for $movieId: ${exception.message}",
                exception
            )
        }
    }

}