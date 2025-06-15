package com.amp.repository

import com.amp.entities.details.MovieDetails
import com.amp.entities.list.Movie

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