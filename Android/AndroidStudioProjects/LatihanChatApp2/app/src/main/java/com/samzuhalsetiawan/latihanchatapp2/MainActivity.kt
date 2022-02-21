package com.samzuhalsetiawan.latihanchatapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp2.adapter.UserAdapter
import com.samzuhalsetiawan.latihanchatapp2.databinding.ActivityMainBinding
import com.samzuhalsetiawan.latihanchatapp2.model.User

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val auth by lazy { Firebase.auth }
    private val database by lazy { Firebase.database("https://latihan-chat-app2-default-rtdb.asia-southeast1.firebasedatabase.app") }
    private val dbRef by lazy { database.reference }
    private val userList = ArrayList<User>()
    private val userAdapter by lazy { UserAdapter(userList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "WhatsApp KW"

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = userAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemLogout -> {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        when (auth.currentUser) {
            null -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else -> {
                dbRef.child("users").addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            userList.clear()
                            for (snap in snapshot.children) {
                                val user = snap.getValue(User::class.java)
                                if (user?.uid == auth.currentUser?.uid) continue
                                user?.let {
                                    userList.add(it)
                                }
                            }
                            userAdapter.notifyDataSetChanged()
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    }
                )
            }
        }
    }

}