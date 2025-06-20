package com.amp.presentation.utils

import com.amp.entities.list.Movie

object MovieGenreUtils {
    private val genreMap = mapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western",
        -1 to "Other"
    )

    fun groupMoviesByGenre(movies: List<Movie>): Map<Int, List<Movie>> {
        val moviesByGenre = mutableMapOf<Int, MutableList<Movie>>()

        movies.forEach { movie ->
            val genreId = movie.genreIds.firstOrNull() ?: -1
            moviesByGenre.getOrPut(genreId) { mutableListOf() }.add(movie)
        }

        return moviesByGenre.toMap()
    }

    fun getGenreName(genreId: Int): String {
        return genreMap[genreId] ?: "Genre $genreId"
    }
}