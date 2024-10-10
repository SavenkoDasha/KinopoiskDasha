package com.example.kinopoiskdasha

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LoginUiState(
    val emailValue: String? = null,
    val passwordValue: String? = null,
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private val users =
        mapOf("dasha@mail.ru" to "1234", "name@mail.ru" to "1333", "elena@gmail.ru" to "1234")

    fun isUserExist(email: String, password: String?): Boolean {
        if (email in users && users[email].equals(password)) {
            return true
        } else {
            return false
        }
    }
}