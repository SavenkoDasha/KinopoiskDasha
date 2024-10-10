package com.example.kinopoiskdasha.ui.screens.login

sealed interface LoginEvent {
    data object OnLoginClicked : LoginEvent
    data class OnEmailChanged(val value: String) : LoginEvent
    data class OnPasswordChanged(val value: String) : LoginEvent
}
