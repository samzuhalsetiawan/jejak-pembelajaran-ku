package com.samzuhalsetiawan.latihanchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp.databinding.ActivityMainBinding
import com.samzuhalsetiawan.latihanchatapp.databinding.ActivitySignUpBinding
import com.samzuhalsetiawan.latihanchatapp.model.User

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val mAuth by lazy { Firebase.auth }
    private val database by lazy { Firebase.database("https://latihan-chat-app-default-rtdb.asia-southeast1.firebasedatabase.app") }
    private val dbRootNode by lazy { database.reference }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            addUserToDatabase(username, email, it.result?.user?.uid)
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        else -> {
                            Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

    }

    private fun addUserToDatabase(username: String, email: String, uid: String?) {
        if (uid == null) return
        dbRootNode.child("users").child(uid).setValue(User(username, email, uid))
    }
}