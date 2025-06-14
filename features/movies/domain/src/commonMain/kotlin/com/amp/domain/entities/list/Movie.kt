package com.amp.domain.entities.list

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val originalLanguage: String,
    val originalTitle: String,
    val adult: Boolean,
    val video: Boolean,
    val genreIds: List<Int>
)
