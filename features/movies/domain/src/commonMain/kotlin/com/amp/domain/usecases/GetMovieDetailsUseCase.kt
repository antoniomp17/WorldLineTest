package com.amp.domain.usecases

import com.amp.domain.entities.details.MovieDetails
import com.amp.domain.repository.MoviesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetMovieDetailsUseCase: KoinComponent {

        private val moviesRepository: MoviesRepository by inject()

        suspend operator fun invoke(movieId: Int): Result<MovieDetails> {
            return moviesRepository.getMovieDetails(movieId)
        }
}