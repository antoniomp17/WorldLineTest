package com.amp.presentation.components.movieslist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amp.presentation.models.movieslist.MoviesListState
import com.amp.presentation.utils.MovieGenreUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListContent(
    state: MoviesListState,
    onMovieClick: (Int) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {

    val moviesByGenre = remember(state.movies) {
        MovieGenreUtils.groupMoviesByGenre(state.movies)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 64.dp
            ),
    ) {

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                moviesByGenre.forEach { (genreId, moviesInGenre) ->
                    item(key = "genre_$genreId") {
                        MoviesListGenreSection(
                            genreName = MovieGenreUtils.getGenreName(genreId),
                            movies = moviesInGenre,
                            onMovieClick = onMovieClick
                        )
                    }
                }
            }
        }
    }
}