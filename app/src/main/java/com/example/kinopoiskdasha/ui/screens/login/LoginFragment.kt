package com.example.kinopoiskdasha.ui.screens.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kinopoiskdasha.R
import com.example.kinopoiskdasha.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewBinding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                viewBinding.loginEmail.newText = state.emailValue
                viewBinding.loginPassword.newText = state.passwordValue
            }
        }

        viewBinding.loginEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.handleEvent(LoginEvent.OnEmailChanged(text.toString()))
        }
        viewBinding.loginPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.handleEvent(LoginEvent.OnPasswordChanged(text.toString()))
        }
        viewBinding.loginBtn.setOnClickListener {
            viewModel.handleEvent(LoginEvent.OnLoginClicked)
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
