package com.example.kinopoiskdasha.ui.screens.movies.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.data.MovieRepository
import com.example.kinopoiskdasha.domain.Result
import com.example.kinopoiskdasha.ui.mapping.mapToDetailUi
import com.example.kinopoiskdasha.ui.mapping.mapToImageItem
import com.example.kinopoiskdasha.ui.model.DetailListItem
import com.example.kinopoiskdasha.ui.model.MockImageItem
import com.example.kinopoiskdasha.ui.model.MovieDetailUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val listItems: List<DetailListItem> = emptyList(),
    val details: MovieDetailUi? = null,
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    val labels = Channel<DetailLabel>()

    init {
        val args: MovieDetailsArgs? = savedStateHandle[DetailFragment.ARGS_KEY]
        args?.id?.let { loadDetail(it) }
    }

    private fun loadDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = movieRepository.fetchMovie(id)
                val responseFrames = movieRepository.getFrames(id)

                when (response) {
                    is Result.Error -> {
                        labels.send(DetailLabel.Exception(response.error.message.toString()))
                    }

                    is Result.Success -> {
                        val detail = response.value.mapToDetailUi()
                        _uiState.update { it.copy(details = detail) }
                    }
                }

                when (responseFrames) {
                    is Result.Error ->
                        labels.send(DetailLabel.Exception(responseFrames.error.message.toString()))

                    is Result.Success -> {
                        val res = responseFrames.value.map { it.mapToImageItem() }

                        if (res.isEmpty()) {
                            _uiState.update { it.copy(listItems = listOf(MockImageItem())) }
                        } else {
                            _uiState.update { it.copy(listItems = res) }
                        }
                    }
                }
            } catch (e: Exception) {
                labels.send(DetailLabel.Exception("Error happened"))
            }
        }
    }

    fun handleEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnBackClicked -> backClicked()
        }
    }

    private fun backClicked() {
        viewModelScope.launch {
            labels.send(DetailLabel.OnNavigateToMoviesScreen)
        }
    }
}
