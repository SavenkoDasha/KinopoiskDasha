package com.example.kinopoiskdasha.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskdasha.data.Provider
import com.example.kinopoiskdasha.data.dto.UserData
import com.example.kinopoiskdasha.data.Provider
import com.example.kinopoiskdasha.data.dto.UserData
import com.example.kinopoiskdasha.data.Provider
import com.example.kinopoiskdasha.data.UserDataSource
import com.example.kinopoiskdasha.data.dto.UserData
import com.example.kinopoiskdasha.ui.screens.login.LoginEvent.OnEmailChanged
import com.example.kinopoiskdasha.ui.screens.login.LoginEvent.OnLoginClicked
import com.example.kinopoiskdasha.ui.screens.login.LoginEvent.OnPasswordChanged
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val emailValue: String = "",
    val passwordValue: String = "",
    var isButtonEnabled: Boolean = false,
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private var emailChangeJob: Job? = null

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

    private fun loginClicked() {
        Provider.dataSource.updateUser(
            UserData(
                email = uiState.value.emailValue,
                password = uiState.value.passwordValue
            )
        )
    }
}
