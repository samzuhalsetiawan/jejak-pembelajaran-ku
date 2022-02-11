package com.samzuhalsetiawan.latihanfirebaseauth2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanfirebaseauth2.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mAuth = Firebase.auth

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        else -> {
                            Toast.makeText(this, "Failed to Login", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        when {
            currentUser != null -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

}