package com.example.kinopoiskdasha.ui.screens.movies.detail

sealed interface DetailLabel {
    data object OnNavigateToMoviesScreen: DetailLabel
    data class Exception(val message: String): DetailLabel
}
