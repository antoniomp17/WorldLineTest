package com.amp

import com.amp.entities.details.Genre
import com.amp.entities.details.MovieDetails
import com.amp.entities.details.ProductionCompany
import com.amp.entities.details.ProductionCountry
import com.amp.entities.details.SpokenLanguage
import com.amp.entities.list.Movie

object TestUtils {
    
    fun createTestMovie(
        id: Int = 1,
        title: String = "Test Movie"
    ): Movie = Movie(
        id = id,
        title = title,
        overview = "Test overview",
        posterPath = "/test_poster.jpg",
        backdropPath = "/test_backdrop.jpg",
        releaseDate = "2025-01-01",
        voteAverage = 7.5,
        voteCount = 1000,
        popularity = 100.0,
        originalLanguage = "en",
        originalTitle = title,
        adult = false,
        video = false,
        genreIds = listOf(28, 12)
    )

    fun createTestMovieDetails(
        id: Int = 1,
        title: String = "Test Movie Details",
        overview: String = "Test detailed overview",
        runtime: Int = 120,
        budget: Int = 100000000,
        revenue: Int = 500000000
    ): MovieDetails = MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterPath = "/test_poster.jpg",
        backdropPath = "/test_backdrop.jpg",
        releaseDate = "2024-01-01",
        voteAverage = 8.0,
        voteCount = 2000,
        popularity = 150.0,
        originalLanguage = "en",
        originalTitle = "$title Original",
        adult = false,
        video = false,
        runtime = runtime,
        budget = budget,
        revenue = revenue,
        homepage = "https://test-movie.com",
        imdbId = "tt1234567",
        status = "Released",
        tagline = "Test tagline for the movie",
        belongsToCollection = null,
        genres = listOf(
            Genre(28, "Action"),
            Genre(12, "Adventure")
        ),
        productionCompanies = listOf(
            ProductionCompany(1, "Test Studios", "/logo.jpg", "US")
        ),
        productionCountries = listOf(
            ProductionCountry("US", "United States")
        ),
        spokenLanguages = listOf(
            SpokenLanguage("English", "en", "English")
        )
    )

    fun createTestGenre(
        id: Int = 28,
        name: String = "Action"
    ): Genre = Genre(id, name)
}