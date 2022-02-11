package com.example.myfirstchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myfirstchatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val myAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val emai = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            myAuth.createUserWithEmailAndPassword(emai, password)
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "User Failed to Add", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

    }
}