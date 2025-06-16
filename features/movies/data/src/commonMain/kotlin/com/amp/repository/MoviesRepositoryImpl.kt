package com.amp.repository

import com.amp.datasources.MoviesDataSource
import com.amp.mappers.toDomain
import com.amp.entities.details.MovieDetails
import com.amp.entities.list.Movie
import com.amp.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val moviesDataSource: MoviesDataSource
) : MoviesRepository {

    override suspend fun getPopularMovies(
        page: Int,
        language: String,
        region: String?
    ): Result<List<Movie>> {
        return try {
            val response = moviesDataSource.getPopularMovies(
                page = page,
                language = language
            )
            if (response.isSuccess) {
                val popularMovies = response.getOrNull()
                if (popularMovies == null || popularMovies.results.isEmpty()) {
                    return Result.failure(Exception("No movies found"))
                }
                Result.success(popularMovies.toDomain())
            } else {
                Result.failure(response.exceptionOrNull()!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(movieId: Int, language: String): Result<MovieDetails> {
        return try {
            val response = moviesDataSource.getMovieDetails(
                movieId = movieId,
                language = language
            )
            if (response.isSuccess) {
                Result.success(response.getOrNull()!!.toDomain())
            } else {
                Result.failure(response.exceptionOrNull()!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}