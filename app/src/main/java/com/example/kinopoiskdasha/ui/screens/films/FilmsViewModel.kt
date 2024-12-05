package com.example.kinopoiskdasha.ui.screens.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.data.MovieRepository
import com.example.kinopoiskdasha.domain.MovieResponse
import com.example.kinopoiskdasha.ui.mapping.mapToUI
import com.example.kinopoiskdasha.ui.model.ListItem
import com.example.kinopoiskdasha.ui.model.toYearItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FilmsUiState(
    val finder: String = "",
    val listItems: List<ListItem> = emptyList(),
    val isSortedDescending: Boolean = false,
    val currentLastPosition: Int = 0,
    val page: Int = 1,
)

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FilmsUiState())
    val uiState: StateFlow<FilmsUiState> = _uiState.asStateFlow()

    val labels = Channel<FilmsLabel>()

    init {
        viewModelScope.launch {
            try {
                val response = movieRepository.getMovieResponse("YEAR", uiState.value.page)
                val films = mapResponseToListItems(response)
                _uiState.update { it.copy(listItems = films) }
            } catch (e: Exception) {
                labels.send(FilmsLabel.Exception("Error happened"))
            }
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
        val res = _uiState.value.listItems
        _uiState.update { it.copy(listItems = res) }
    }

    private fun logOutClicked() {
        viewModelScope.launch {
            labels.send(FilmsLabel.OnNavigateToLogin)
        }
    }

    private fun positionChanged(position: Int) {
        if (position == _uiState.value.currentLastPosition) return

        _uiState.update { it.copy(currentLastPosition = position) }

        if (position == _uiState.value.listItems.lastIndex) {
            _uiState.update { it.copy(page = it.page + 1) }

            viewModelScope.launch {
                val response = movieRepository.getMovieResponse("YEAR", uiState.value.page)
                val films = mapResponseToListItems(response)
                _uiState.update { it.copy(listItems = it.listItems.plus(films)) }
            }
        }
    }

    private fun mapResponseToListItems(response: MovieResponse) = buildList {
        response.items.forEach {
            val movie = it.mapToUI()
            val year = it.startYear.toString().toYearItem()

            if (!this.contains(year) && !_uiState.value.listItems.contains(year)) add(year)
            add(movie)
        }
    }
}

