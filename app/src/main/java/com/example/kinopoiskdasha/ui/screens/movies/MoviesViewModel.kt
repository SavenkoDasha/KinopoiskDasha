package com.example.kinopoiskdasha.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.data.MovieRepository
import com.example.kinopoiskdasha.domain.Result
import com.example.kinopoiskdasha.domain.MovieResponse
import com.example.kinopoiskdasha.ui.mapping.mapToUI
import com.example.kinopoiskdasha.ui.model.ListItem
import com.example.kinopoiskdasha.ui.model.MockItem
import com.example.kinopoiskdasha.ui.model.RecyclerItem
import com.example.kinopoiskdasha.ui.model.toYearItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MoviesUiState(
    val finder: String = "",
    val listItems: List<ListItem> = emptyList(),
    val isSortedDescending: Boolean = false,
    val currentLastPosition: Int = 0,
    val page: Int = 1,
)

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    val labels = Channel<MoviesLabel>()

    init {
        _uiState.update { it.copy(listItems = listOf(getMockRecyclerItem())) }

        viewModelScope.launch {
            try {
                val response = movieRepository.getMovieResponse("YEAR", uiState.value.page)
                when (response) {
                    is Result.Error -> {
                        labels.send(MoviesLabel.Exception(response.error.message.toString()))
                    }
                    is Result.Success -> {
                        val movies = mapResponseToListItems(response.value)
                        movieRepository.saveMovies(*response.value.items.toTypedArray())
                        _uiState.update { it.copy(listItems = it.listItems + movies) }}
                }
            } catch (e: Exception) {
                labels.send(MoviesLabel.Exception("Error happened"))
            }
        }
    }

    fun handleEvent(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.OnLogOutClicked -> logOutClicked()
            is MoviesEvent.OnScrollPositionChanged -> positionChanged(event.position)
            is MoviesEvent.OnSortClicked -> sortMovies()
        }
    }

    private fun sortMovies() {
        val res = _uiState.value.listItems
        _uiState.update { it.copy(listItems = res) }
    }

    private fun logOutClicked() {
        viewModelScope.launch {
            labels.send(MoviesLabel.OnNavigateToLogin)
        }
    }

    private fun positionChanged(position: Int) {
        if (position == _uiState.value.currentLastPosition) return

        _uiState.update { it.copy(currentLastPosition = position) }

        if (position == _uiState.value.listItems.lastIndex) {
            _uiState.update { it.copy(page = it.page + 1) }

            viewModelScope.launch {
                val response = movieRepository.getMovieResponse("YEAR", uiState.value.page)
                when (response) {
                    is Result.Error -> {
                        labels.send(MoviesLabel.Exception(response.error.message.toString()))
                    }
                    is Result.Success -> {
                        val movies = mapResponseToListItems(response.value)
                        movieRepository.saveMovies(*response.value.items.toTypedArray())

                        _uiState.update { it.copy(listItems = it.listItems.plus(movies)) }
                    }
                }
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

    private fun getMockRecyclerItem() = RecyclerItem(
        buildList {
            repeat(20) {
                add(MockItem(it.toString()))
            }
        }
    )
}
