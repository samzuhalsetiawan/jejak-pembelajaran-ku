package com.samzuhalsetiawan.latihanfirebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanfirebaseauth.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Failed create user", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        binding.btnLogin.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }

    }

}