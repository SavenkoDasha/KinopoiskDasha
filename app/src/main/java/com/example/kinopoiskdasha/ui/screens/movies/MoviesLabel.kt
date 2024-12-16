package com.example.kinopoiskdasha.ui.screens.movies

sealed interface MoviesLabel {
    data object OnNavigateToLogin: MoviesLabel
    data object OnNavigateToMovieDetail: MoviesLabel
    data class Exception(val message: String): MoviesLabel
}
