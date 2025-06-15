package com.amp.usecases

import com.amp.entities.list.Movie
import com.amp.repository.MoviesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.Result

class GetPopularMoviesUseCase: KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    suspend operator fun invoke(page: Int): Result<List<Movie>> {
        return runCatching {
            val movies = moviesRepository.getPopularMovies(page).getOrThrow()
            movies.filter { movie ->
                movie.id > 0 && movie.title.isNotBlank()
            }
        }
    }
}