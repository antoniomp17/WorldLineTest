package com.amp.presentation.utils

object ImageUtils {
    
    private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

    enum class PosterSize(val value: String) {
        W92("w92"),
        W154("w154"),
        W185("w185"),
        W342("w342"),
        W500("w500"),
        W780("w780"),
        ORIGINAL("original")
    }

    fun buildPosterUrl(
        posterPath: String?,
        size: PosterSize = PosterSize.W500
    ): String? {
        return if (!posterPath.isNullOrBlank()) {
            "$TMDB_IMAGE_BASE_URL${size.value}$posterPath"
        } else {
            null
        }
    }
}