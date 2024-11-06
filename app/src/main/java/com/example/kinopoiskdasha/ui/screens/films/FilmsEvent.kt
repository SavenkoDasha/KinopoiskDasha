package com.example.kinopoiskdasha.ui.screens.films

sealed interface FilmsEvent {
    data object onLogOutClicked: FilmsEvent
}
