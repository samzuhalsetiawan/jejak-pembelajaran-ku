package com.samzuhalsetiawan.latihanchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp.adapter.PrivateMessageAdapter
import com.samzuhalsetiawan.latihanchatapp.data.AppData
import com.samzuhalsetiawan.latihanchatapp.databinding.ActivityPrivateMessageBinding
import com.samzuhalsetiawan.latihanchatapp.model.Message
import com.samzuhalsetiawan.latihanchatapp.utils.AdapterInstance

class PrivateMessageActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "EXTRA_USERNAME"
        const val EXTRA_RECEIVER_UID = "EXTRA_RECEIVER_UID"
    }

    private val binding by lazy { ActivityPrivateMessageBinding.inflate(layoutInflater) }
    private val database by lazy { Firebase.database("https://latihan-chat-app-default-rtdb.asia-southeast1.firebasedatabase.app") }
    private val dbRef by lazy { database.reference }
    private val mAuth by lazy { Firebase.auth }
    private val senderUid by lazy { mAuth.currentUser?.uid.toString() }
    private val recvUid by lazy { intent.getStringExtra(EXTRA_RECEIVER_UID).toString() }
    private val senderRoom by lazy { senderUid + recvUid }
    private val receiverRoom by lazy { recvUid + senderUid }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvPrivateMessage.layoutManager = LinearLayoutManager(this)
        binding.rvPrivateMessage.adapter = AdapterInstance.messageAdapter

        binding.btnKirim.setOnClickListener {
            val pesan = binding.etInputMessage.text.toString()
            simpanPesanKeDatabase(pesan)
            binding.etInputMessage.setText("")
        }
        dbRef.child("chats").child(senderRoom).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                AppData.listMessage.clear()
                for (snap in snapshot.children) {
                    val message = snap.getValue(Message::class.java)
                    message?.let {
                        AppData.listMessage.add(it)
                        AdapterInstance.messageAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun simpanPesanKeDatabase(pesan: String) {

        val message = Message(pesan, senderUid)
        dbRef.child("chats").child(senderRoom).push()
            .setValue(message).addOnCompleteListener {
                when {
                    it.isSuccessful -> {
                        dbRef.child("chats").child(receiverRoom).push()
                            .setValue(message)
                    }
                }
            }
    }
}