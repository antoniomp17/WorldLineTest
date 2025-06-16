package com.amp.presentation.models.movieslist

import com.amp.entities.list.Movie

data class MoviesListState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val currentPage: Int = 0,
    val hasMorePages: Boolean = true,
    val isEmpty: Boolean = false
) {

    val shouldShowContent: Boolean
        get() = !isLoading && errorMessage == null && !isEmpty

    val shouldShowEmpty: Boolean
        get() = !isLoading && movies.isEmpty() && errorMessage == null

    val shouldShowError: Boolean
        get() = errorMessage != null && !isLoading

    val canLoadMore: Boolean
        get() = hasMorePages && !isLoading && errorMessage == null
}