package com.samzuhalsetiawan.latihanchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp.adapter.UserAdapter
import com.samzuhalsetiawan.latihanchatapp.data.AppData
import com.samzuhalsetiawan.latihanchatapp.databinding.ActivityMainBinding
import com.samzuhalsetiawan.latihanchatapp.model.User
import com.samzuhalsetiawan.latihanchatapp.utils.AdapterInstance
import com.samzuhalsetiawan.latihanchatapp.utils.DataChangeListener

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mAuth by lazy { Firebase.auth }
    private val database by lazy { Firebase.database("https://latihan-chat-app-default-rtdb.asia-southeast1.firebasedatabase.app") }
    private val dbRef by lazy { database.reference }
    private val rvListUser by lazy { binding.rvListUser }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogOut.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
        }

        rvListUser.adapter = AdapterInstance.listUserAdapter
        rvListUser.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()

        when (mAuth.currentUser) {
            null -> startActivity(Intent(this, SignInActivity::class.java))
            else -> {
                dbRef.child("users").addValueEventListener(DataChangeListener)
            }
        }

    }

}