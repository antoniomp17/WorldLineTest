package com.amp.presentation.components.movieslist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val moviesByGenre = remember(state.movies) {
        MovieGenreUtils.groupMoviesByGenre(state.movies)
    }

    Scaffold(
        floatingActionButton = {
            LoadMoreButton(
                hasMorePages = state.hasMorePages,
                isLoading = state.isLoading,
                onLoadMore = onLoadMore
            )
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = "Popular Movies",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )

        }
    ) { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize().padding(paddingValues),
        ) {

            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 88.dp
                    ),
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
}