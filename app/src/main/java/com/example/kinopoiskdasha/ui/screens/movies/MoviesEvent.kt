package com.example.kinopoiskdasha.ui.screens.movies

sealed interface MoviesEvent {
    data object OnLogOutClicked: MoviesEvent
    data object OnSortClicked: MoviesEvent
    data class OnScrollPositionChanged(val position: Int) : MoviesEvent
}
