package com.amp.integration

import com.amp.entities.list.Movie
import com.amp.repository.MoviesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetPopularMoviesUseCase: KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    suspend operator fun invoke(page: Int): Result<List<Movie>> {
        return moviesRepository.getPopularMovies(page)
    }

}