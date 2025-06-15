package com.amp.integration

import com.amp.entities.details.MovieDetails
import com.amp.repository.MoviesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetMovieDetailsUseCase: KoinComponent {

        private val moviesRepository: MoviesRepository by inject()

        suspend operator fun invoke(movieId: Int): Result<MovieDetails> {
            return moviesRepository.getMovieDetails(movieId)
        }
}