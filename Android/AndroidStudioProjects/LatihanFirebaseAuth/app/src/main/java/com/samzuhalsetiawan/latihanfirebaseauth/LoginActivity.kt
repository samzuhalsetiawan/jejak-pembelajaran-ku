package com.samzuhalsetiawan.latihanfirebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanfirebaseauth.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            Toast.makeText(this, "Login Succes", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        else -> {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser

        checkIfUserAlreadyLogin(currentUser)
    }

    private fun checkIfUserAlreadyLogin(currentUser: FirebaseUser?) {
        when {
            currentUser != null -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

}