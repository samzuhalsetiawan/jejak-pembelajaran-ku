package com.example.storyapp.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentLoginBinding
import com.example.storyapp.domain.utils.viewBindings
import com.example.storyapp.presentation.authentication.AuthenticationActivity
import com.example.storyapp.presentation.authentication.AuthenticationViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBindings(FragmentLoginBinding::bind)
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            val extraEmail = bundle.getString(AuthenticationActivity.EXTRA_EMAIL)
            extraEmail?.let { binding.etEmail.setText(it) }
        }

        binding.etlPassword.setupWithButton(binding.btnLogin, binding.etlEmail)
        binding.etlEmail.setupWithButton(binding.btnLogin, binding.etlPassword)

        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener {
            authenticationViewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

}