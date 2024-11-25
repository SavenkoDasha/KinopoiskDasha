package com.example.kinopoiskdasha.ui.screens.login

sealed interface LoginLabel {
    data object OnNavigateToMovies : LoginLabel
    data class Exception(val message : String) : LoginLabel
}
