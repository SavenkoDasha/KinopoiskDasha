package com.example.kinopoiskdasha.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.data.UserRepository
import com.example.kinopoiskdasha.domain.User
import com.example.kinopoiskdasha.ui.screens.login.LoginEvent.OnEmailChanged
import com.example.kinopoiskdasha.ui.screens.login.LoginEvent.OnLoginClicked
import com.example.kinopoiskdasha.ui.screens.login.LoginEvent.OnPasswordChanged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val emailValue: String = "",
    val passwordValue: String = "",
    var isButtonEnabled: Boolean = false,
    val currentUser: User? = null,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    val labels = Channel<LoginLabel>()

    private var emailChangeJob: Job? = null

    init {
        viewModelScope.launch {
            val currentUser = userRepository.getUser()
            if (currentUser != null) {
                _uiState.update { it.copy(currentUser = currentUser, emailValue = currentUser.email) }
            }
        }
    }

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is OnPasswordChanged -> changePassword(event.value)
            is OnEmailChanged -> changeEmail(event.value)
            is OnLoginClicked -> loginClicked()
        }
    }

    private fun changeEmail(new: String) {
        emailChangeJob?.cancel()
        _uiState.update { it.copy(emailValue = new) }
        emailChangeJob = viewModelScope.launch {
            _uiState.update {
                delay(300L)
                it.copy(isButtonEnabled = new.length >= 3)
            }
        }
    }

    private fun changePassword(new: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(passwordValue = new) }
        }
    }

    // password "11111", email "dasha@mail.ru"
    private fun loginClicked() {
        viewModelScope.launch {
            with(uiState.value) {
                val prevUser = userRepository.getUser()

                if (prevUser == null || emailValue != prevUser.email) {
                    userRepository.saveUser(User(emailValue, passwordValue))
                    labels.send(LoginLabel.OnNavigateToMovies)
                } else if (passwordValue == prevUser.password) {
                    labels.send(LoginLabel.OnNavigateToMovies)
                } else {
                    labels.send(LoginLabel.Exception("Incorrect password or email"))
                }
            }
        }
    }
}
