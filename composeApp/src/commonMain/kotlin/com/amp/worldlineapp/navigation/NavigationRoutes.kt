package com.amp.worldlineapp.navigation

object NavigationRoutes {

    const val MOVIES_LIST = "movies_list"
    const val MOVIE_DETAILS = "movie_details"
    const val MOVIE_ID_ARG = "movieId"
    const val MOVIE_DETAILS_WITH_ID = "$MOVIE_DETAILS/{$MOVIE_ID_ARG}"

    fun movieDetailsRoute(movieId: Int): String {
        return "$MOVIE_DETAILS/$movieId"
    }
}