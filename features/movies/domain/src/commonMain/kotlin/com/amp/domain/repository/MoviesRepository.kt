package com.amp.domain.repository

import com.amp.domain.entities.details.MovieDetails
import com.amp.domain.entities.list.Movie

interface MoviesRepository {

    suspend fun getPopularMovies(
        page: Int = 1,
        language: String = "en-US",
        region: String? = null
    ): Result<List<Movie>>

    suspend fun getMovieDetails(
        movieId: Int,
        language: String = "en-US"
    ): Result<MovieDetails>

}