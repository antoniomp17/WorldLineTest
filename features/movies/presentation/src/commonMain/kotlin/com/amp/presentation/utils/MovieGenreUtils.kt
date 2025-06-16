package com.amp.presentation.utils

import com.amp.entities.list.Movie

object MovieGenreUtils {

    fun groupMoviesByGenre(movies: List<Movie>): Map<Int, List<Movie>> {
        val moviesByGenre = mutableMapOf<Int, MutableList<Movie>>()

        movies.forEach { movie ->
            val genreId = movie.genreIds.firstOrNull() ?: -1
            moviesByGenre.getOrPut(genreId) { mutableListOf() }.add(movie)
        }

        return moviesByGenre.toMap()
    }

    fun getGenreName(genreId: Int): String {
        return when (genreId) {
            28 -> "Action"
            12 -> "Adventure"
            16 -> "Animation"
            35 -> "Comedy"
            80 -> "Crime"
            99 -> "Documentary"
            18 -> "Drama"
            10751 -> "Family"
            14 -> "Fantasy"
            36 -> "History"
            27 -> "Horror"
            10402 -> "Music"
            9648 -> "Mystery"
            10749 -> "Romance"
            878 -> "Science Fiction"
            10770 -> "TV Movie"
            53 -> "Thriller"
            10752 -> "War"
            37 -> "Western"
            -1 -> "Other"
            else -> "Genre $genreId"
        }
    }
}