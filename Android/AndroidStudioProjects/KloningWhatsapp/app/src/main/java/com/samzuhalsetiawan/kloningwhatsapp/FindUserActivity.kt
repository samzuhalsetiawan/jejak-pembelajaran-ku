package com.samzuhalsetiawan.kloningwhatsapp

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.loader.app.LoaderManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.kloningwhatsapp.adapter.UserListAdapter
import com.samzuhalsetiawan.kloningwhatsapp.databinding.ActivityFindUserBinding
import com.samzuhalsetiawan.kloningwhatsapp.model.UserObject
import com.samzuhalsetiawan.kloningwhatsapp.utils.UserPhoneContacts

class FindUserActivity : AppCompatActivity()
{

    private val binding by lazy { ActivityFindUserBinding.inflate(layoutInflater) }
    private val userList = ArrayList<UserObject>()
    private val userListAdapter by lazy { UserListAdapter(userList) }
    private val database by lazy { Firebase.database("https://kloning-whatsapp-d26a8-default-rtdb.asia-southeast1.firebasedatabase.app/") }
    private val dbRef by lazy { database.reference }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvUserList.isNestedScrollingEnabled = false
        binding.rvUserList.setHasFixedSize(false)
        binding.rvUserList.layoutManager = LinearLayoutManager(this)
        binding.rvUserList.adapter = userListAdapter

        getUserFromDatabase()

    }

    private fun getUserFromDatabase() {
        dbRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("TAG", "FIREBASE SINGLE VALUE EVENT TRIGERED")
                for (childNode in snapshot.children) {
                    val data = childNode.getValue(UserObject::class.java)
                    data?.let {
                        UserPhoneContacts.findContact(it.phoneNumber)?.let { user ->
                            userList.add(user)
                            userListAdapter.notifyDataSetChanged()
                        } ?: Log.e("TAG", "USER WITH PHONE: ${it.phoneNumber} NOT FOUND")
                    } ?: Log.e("TAG", "SNAPSHOT NULL")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}