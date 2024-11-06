package com.example.kinopoiskdasha.ui.screens.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.domain.Movie
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FilmsUiState(
    var finder: String = "",
    val films: List<Movie> = emptyList(),
    val isSortedDescending: Boolean = false,
)

class FilmsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FilmsUiState())
    val uiState: StateFlow<FilmsUiState> = _uiState.asStateFlow()

    val labels = Channel<FilmsLabel>()

    fun handleEvent(event: FilmsEvent) {
        when (event) {
            is FilmsEvent.onLogOutClicked -> logOutClicked()
        }
    }

    private fun logOutClicked() {
        viewModelScope.launch {
            labels.send(FilmsLabel.OnNavigateToLogin)
        }
    }
}
