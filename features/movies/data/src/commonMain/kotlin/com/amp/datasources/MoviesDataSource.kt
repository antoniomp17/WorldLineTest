package com.amp.datasources

class MoviesDataSource(private val api: Api) {
    suspend fun getPopularMovies(
        page: Int = 1,
        language: String = "en-US"
    ) = api.getPopularMovies(
        page = page,
        language = language
    )

    suspend fun getMovieDetails(
        movieId: Int,
        language: String = "en-US"
    ) = api.getMovieDetails(
        movieId = movieId,
        language = language
    )
}