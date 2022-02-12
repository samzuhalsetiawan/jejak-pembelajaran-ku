package com.samzuhalsetiawan.latihanchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val mAuth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
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

        binding.btnSignUp.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }

    }
}