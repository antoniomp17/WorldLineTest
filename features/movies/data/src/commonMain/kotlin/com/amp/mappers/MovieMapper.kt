package com.amp.mappers

import com.amp.models.movielist.PopularMovieResponse
import com.amp.entities.list.Movie

fun PopularMovieResponse.toDomain(): List<Movie> {
    return results.map {
        Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            popularity = it.popularity,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            adult = it.adult,
            video = it.video,
            genreIds = it.genreIds,
            backdropPath = it.backdropPath
        )
    }
}