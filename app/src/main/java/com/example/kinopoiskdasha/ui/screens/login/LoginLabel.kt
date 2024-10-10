package com.example.kinopoiskdasha.ui.screens.login

sealed interface LoginLabel {
    data object OnNavigateToMovies : LoginLabel
}
