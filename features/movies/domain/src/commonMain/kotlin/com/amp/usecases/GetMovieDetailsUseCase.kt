package com.amp.usecases

import com.amp.entities.details.MovieDetails
import com.amp.repository.MoviesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class GetMovieDetailsUseCase: KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    open suspend operator fun invoke(
        movieId: Int,
        language: String = "en-US"
    ): Result<MovieDetails> {
        if (movieId <= 0) {
            return Result.failure(IllegalArgumentException("Invalid movie ID: $movieId. Movie ID must be a positive number."))
        }
        return runCatching {
            moviesRepository.getMovieDetails(movieId, language)
                .fold(
                    onSuccess = { details ->
                        if (details.id != movieId) {
                            throw IllegalStateException("Film ID mismatch: Requested $movieId but got ${details.id}")
                        }
                        details
                    },
                    onFailure = { e ->
                        throw Exception("Error retrieving movie details for $movieId", e)
                    }
                )
        }
    }
}