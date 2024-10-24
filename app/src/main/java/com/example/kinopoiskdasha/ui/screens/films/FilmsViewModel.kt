package com.example.kinopoiskdasha.ui.screens.films

import androidx.lifecycle.ViewModel
import com.example.kinopoiskdasha.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FilmsUiState(
    val emailValue: String = "",
    val passwordValue: String = "",
    var isButtonEnabled: Boolean = false,
    val currentUser: User? = null,
)

class FilmsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FilmsUiState())
    val uiState: StateFlow<FilmsUiState> = _uiState.asStateFlow()
}