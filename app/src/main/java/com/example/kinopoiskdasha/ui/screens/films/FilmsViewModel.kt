package com.example.kinopoiskdasha.ui.screens.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.data.Provider
import com.example.kinopoiskdasha.ui.mapping.mapToUI
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
    val currentLastPosition: Int = 0,
    val page: Int = 1,
)

class FilmsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FilmsUiState())
    val uiState: StateFlow<FilmsUiState> = _uiState.asStateFlow()
    val labels = Channel<FilmsLabel>()

    init {
        viewModelScope.launch {
            val response = Provider.movieRepository.getMovieResponse(_uiState.value.page)
            val films = response.items.map { it.mapToUI() }
            _uiState.update { it.copy(films = films) }
        }
    }

    fun handleEvent(event: FilmsEvent) {
        when (event) {
            is FilmsEvent.OnLogOutClicked -> logOutClicked()
            is FilmsEvent.OnScrollPositionChanged -> positionChanged(event.position)
            is FilmsEvent.OnSortClicked -> sortFilms()
        }
    }

    private fun sortFilms() {
        val res = _uiState.value.films.sortedBy {
            it.filmYearAndCountry.substring(0, 4) }
        _uiState.update { it.copy(films = res) }
    }

    private fun logOutClicked() {
        viewModelScope.launch {
            labels.send(FilmsLabel.OnNavigateToLogin)
        }
    }

    private fun positionChanged(position: Int) {
        if (position == _uiState.value.currentLastPosition) return

        _uiState.update { it.copy(currentLastPosition = position) }

        if (position == _uiState.value.films.lastIndex) {
            _uiState.update { it.copy(page = it.page + 1) }

            viewModelScope.launch {
                val response = Provider.movieRepository.getMovieResponse(_uiState.value.page)
                val films = response.items.map { it.mapToUI() }
                _uiState.update { it.copy(films = it.films.plus(films)) }
            }
        }
    }
}
