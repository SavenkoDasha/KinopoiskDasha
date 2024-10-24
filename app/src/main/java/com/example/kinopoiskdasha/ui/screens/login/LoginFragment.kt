package com.example.kinopoiskdasha.ui.screens.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kinopoiskdasha.R
import com.example.kinopoiskdasha.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewBinding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            lifecycleScope.launch {
                viewModel.uiState.collect { state ->
                    loginEmail.newText = state.emailValue
                    loginPassword.newText = state.passwordValue
                    loginBtn.isEnabled = state.isButtonEnabled
                }
            }

            loginEmail.doOnTextChanged { text, _, _, _ ->
                viewModel.handleEvent(LoginEvent.OnEmailChanged(text.toString()))
            }
            loginPassword.doOnTextChanged { text, _, _, _ ->
                viewModel.handleEvent(LoginEvent.OnPasswordChanged(text.toString()))
            }
            loginBtn.setOnClickListener {
                viewModel.handleEvent(LoginEvent.OnLoginClicked)
                findNavController().navigate(R.id.action_loginFragment_to_filmsFragment)
            }
        }
    }
}

var EditText.newText: String?
    get() = text.toString()
    set(value) {
        if (text.toString() != value) {
            setText(value)
        }
    }
