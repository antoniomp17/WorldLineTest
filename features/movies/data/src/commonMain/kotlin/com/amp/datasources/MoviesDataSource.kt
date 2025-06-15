package com.amp.datasources

class MoviesDataSource(private val api: Api) {
    suspend fun getPopularMovies() = api.getPopularMovies()
}