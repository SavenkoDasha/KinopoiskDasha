package com.example.kinopoiskdasha

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kinopoiskdasha.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewBinding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            loginBtn.setOnClickListener {
                val email = viewBinding.loginEmail.text.toString()
                val password = viewBinding.loginPassword.text.toString()

                val user = viewModel.isUserExist(email, password)
                if (!user) {
                    Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}