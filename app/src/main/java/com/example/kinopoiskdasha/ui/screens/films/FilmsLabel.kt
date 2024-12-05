package com.example.kinopoiskdasha.ui.screens.films

sealed interface FilmsLabel {
    data object OnNavigateToLogin: FilmsLabel
    data object OnNavigateToFilmDetail: FilmsLabel
    data class Exception(val message: String): FilmsLabel
}
