package com.example.kinopoiskdasha.ui.screens.films

sealed interface FilmsEvent {
    data object OnLogOutClicked: FilmsEvent
    data object OnSortClicked: FilmsEvent
    data class OnScrollPositionChanged(val position: Int) : FilmsEvent
}
