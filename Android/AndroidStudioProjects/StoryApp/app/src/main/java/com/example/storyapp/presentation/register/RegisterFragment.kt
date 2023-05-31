package com.example.storyapp.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.storyapp.R
import com.example.storyapp.databinding.FragmentRegisterBinding
import com.example.storyapp.domain.utils.viewBindings
import com.example.storyapp.presentation.authentication.AuthenticationViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBindings(FragmentRegisterBinding::bind)
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etlConfirmPassword.addPasswordVerifiedWith(binding.etlPassword)
        binding.etlConfirmPassword.setupWithButton(binding.btnRegister, binding.etlUsername, binding.etlEmail, binding.etlPassword)
        binding.etlPassword.setupWithButton(binding.btnRegister, binding.etlUsername, binding.etlEmail, binding.etlConfirmPassword)
        binding.etlEmail.setupWithButton(binding.btnRegister, binding.etlUsername, binding.etlConfirmPassword, binding.etlPassword)

        binding.btnRegister.setOnClickListener {
            authenticationViewModel.register(
                binding.etUsername.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

    }
}