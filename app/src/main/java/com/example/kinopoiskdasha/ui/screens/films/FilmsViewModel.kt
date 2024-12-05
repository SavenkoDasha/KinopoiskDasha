package com.example.kinopoiskdasha.ui.screens.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.data.Provider
import com.example.kinopoiskdasha.ui.mapping.mapToUI
import com.example.kinopoiskdasha.ui.model.ListItem
import com.example.kinopoiskdasha.ui.model.MovieUi
import com.example.kinopoiskdasha.ui.model.YearItem
import com.example.kinopoiskdasha.ui.model.toYearItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FilmsUiState(
    val finder: String = "",
    val listItems: List<ListItem> = emptyList(),
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
            val films: List<ListItem> = response.items.map { it.mapToUI() }

            val yearItems: List<ListItem> = films.groupByYear()

            _uiState.update { it.copy(listItems = yearItems) }
        }
    }

    fun handleEvent(event: FilmsEvent) {
        when (event) {
            is FilmsEvent.OnLogOutClicked -> logOutClicked()
            is FilmsEvent.OnScrollPositionChanged -> positionChanged(event.position)
            is FilmsEvent.OnSortClicked -> sortFilms()
        }
    }

    private val yearComparator = Comparator { item1: ListItem, item2: ListItem ->
        return@Comparator if(item1 is MovieUi && item2 is MovieUi) {
            item1.filmYearAndCountry.substring(0, 4).compareTo(item2.filmYearAndCountry.substring(0, 4))
        }else if(item1 is YearItem && item2 is YearItem){
            item1.year.compareTo(item2.year)
        }else if(item1 is YearItem){
            -1
        }else{
            1
        }
    }
    private fun sortFilms() {
        val res = _uiState.value.listItems
            .sortedWith(yearComparator)
            .groupByYear()
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
                val response = Provider.movieRepository.getMovieResponse(_uiState.value.page)
                val films = response.items.map { it.mapToUI() }
                _uiState.update { it.copy(listItems = it.listItems.plus(films)) }
            }
        }
    }
}

private fun List<ListItem>.groupByYear(): List<ListItem> {
    return this
        .filterIsInstance<MovieUi>()
        .groupBy { it.filmYearAndCountry.substring(0, 4).toYearItem() }
        .flatMap { listOf(it.key) + it.value }
}
