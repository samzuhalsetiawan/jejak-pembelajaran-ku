package com.samzuhalsetiawan.latihanchatapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp2.databinding.ActivitySignUpBinding
import com.samzuhalsetiawan.latihanchatapp2.model.User

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val auth by lazy { Firebase.auth }
    private val database by lazy { Firebase.database("https://latihan-chat-app2-default-rtdb.asia-southeast1.firebasedatabase.app") }
    private val dbRef by lazy { database.reference }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            signUpUser(username, email, password)
        }

    }

    private fun signUpUser(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    addUserToDatabase(username, email, it.result?.user?.uid)
                    finish()
                }
                else -> Toast.makeText(this, "Failed to Create Sign Up", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserToDatabase(username: String, email: String, uid: String?) {
        uid ?: return
        val user = User(username, email, uid)
        dbRef.child("users").child(uid).setValue(user)
    }
}