package com.example.kinopoiskdasha.ui.screens.movies.detail

sealed interface DetailEvent {
    data object OnBackClicked: DetailEvent
}
