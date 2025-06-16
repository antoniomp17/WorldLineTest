package com.amp.worldlineapp.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amp.presentation.screens.MovieDetailsScreen
import com.amp.presentation.screens.MoviesListScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.MOVIES_LIST
    ) {
        composable(route = NavigationRoutes.MOVIES_LIST) {
            MoviesListScreen(
                onNavigateToDetails = { movieId ->
                    navController.navigate(NavigationRoutes.movieDetailsRoute(movieId))
                },
                onShowMessage = { message ->
                    scope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            )
        }

        composable(route = NavigationRoutes.MOVIE_DETAILS_WITH_ID) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString(NavigationRoutes.MOVIE_ID_ARG)?.toIntOrNull()
            
            if (movieId != null) {
                MovieDetailsScreen(
                    movieId = movieId,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onShowMessage = { message ->
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                )
            } else {
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar("Invalid movie ID")
                    navController.popBackStack()
                }
            }
        }
    }
}