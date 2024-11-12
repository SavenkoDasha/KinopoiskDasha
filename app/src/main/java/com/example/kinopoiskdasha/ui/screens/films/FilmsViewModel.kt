package com.example.kinopoiskdasha.ui.screens.films

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.data.Provider
import com.example.kinopoiskdasha.domain.Movie
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FilmsUiState(
    val finder: String = "",
    val films: List<Movie> = emptyList(),
    val isSortedDescending: Boolean = false,
)

class FilmsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FilmsUiState())
    val uiState: StateFlow<FilmsUiState> = _uiState.asStateFlow()
    val labels = Channel<FilmsLabel>()

    init {
        viewModelScope.launch {
            val response = Provider.movieRepository.getMovieResponse(1)
            _uiState.update { it.copy(films = response.items) }
            Log.d("", response.items.toString())
        }
    }

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
