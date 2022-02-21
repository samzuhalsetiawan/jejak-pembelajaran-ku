package com.samzuhalsetiawan.latihanchatapp.utils

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp.TAG
import com.samzuhalsetiawan.latihanchatapp.data.AppData.listUser
import com.samzuhalsetiawan.latihanchatapp.model.User

object DataChangeListener: ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        Log.d(TAG, "Clearing previous list user...")
        listUser.clear()
        Log.d(TAG, "onDataChange Trigered, Processing...")
        for (snap in snapshot.children) {
            Log.d(TAG, "For loop, Data Procesed...")
            val value = snap.getValue(User::class.java)
            if (value != null) {
                if (value.uid == Firebase.auth.currentUser?.uid) continue
                listUser.add(value)
                Log.d(TAG, "Data procesed succesfully")
                Log.d(TAG, listUser.toList().toString())
                Log.d(TAG, "Notify Recycler view adapter")
                AdapterInstance.listUserAdapter.notifyDataSetChanged()
            } else {
                Log.d(TAG, "Data procesed Failed, value is null, exit")
                Log.d(TAG, listUser.toList().toString())
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {

    }
}