package com.samzuhalsetiawan.latihanchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp.adapter.UserAdapter
import com.samzuhalsetiawan.latihanchatapp.databinding.ActivityMainBinding
import com.samzuhalsetiawan.latihanchatapp.model.User

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mAuth by lazy { Firebase.auth }
    private val rvListUser by lazy { binding.rvListUser }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogOut.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
        }

        val listUser = listOf<User>()

        rvListUser.adapter = UserAdapter(listUser)

    }

    override fun onStart() {
        super.onStart()

        when (mAuth.currentUser) {
            null -> startActivity(Intent(this, SignInActivity::class.java))
        }

    }

}