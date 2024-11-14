package com.example.kinopoiskdasha.ui.screens.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.data.MovieMapper
import com.example.kinopoiskdasha.data.Provider
import com.example.kinopoiskdasha.ui.model.MovieUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FilmsUiState(
    val finder: String = "",
    val films: List<MovieUi> = emptyList(),
    val isSortedDescending: Boolean = false,
)

class FilmsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FilmsUiState())
    val uiState: StateFlow<FilmsUiState> = _uiState.asStateFlow()
    val labels = Channel<FilmsLabel>()

    init {
        viewModelScope.launch {
            val response = Provider.movieRepository.getMovieResponse(1)
            val mapper = MovieMapper()
            _uiState.update { it.copy(films = mapper.mapDomainToUI(response.items)) }
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
