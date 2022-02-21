package com.samzuhalsetiawan.latihanchatapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp2.adapter.PrivateMessageAdapter
import com.samzuhalsetiawan.latihanchatapp2.databinding.ActivityPrivateMessageBinding
import com.samzuhalsetiawan.latihanchatapp2.model.PrivateMessage

class PrivateMessageActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RECEIVER_USERNAME = "EXTRA_RECEIVER_USERNAME"
        const val EXTRA_RECEIVER_UID = "EXTRA_RECEIVER_UID"
    }

    private val binding by lazy { ActivityPrivateMessageBinding.inflate(layoutInflater) }
    private val recvUserName by lazy { intent.getStringExtra(EXTRA_RECEIVER_USERNAME) }
    private val recvUid by lazy { intent.getStringExtra(EXTRA_RECEIVER_UID) }
    private val listPrivateMessage = ArrayList<PrivateMessage>()
    private val privateMessageAdapter by lazy { PrivateMessageAdapter(listPrivateMessage) }
    private val database by lazy { Firebase.database("https://latihan-chat-app2-default-rtdb.asia-southeast1.firebasedatabase.app") }
    private val dbRef by lazy { database.reference }
    private val auth by lazy { Firebase.auth }
    private val senderRoomChat by lazy { auth.currentUser?.uid + recvUid }
    private val receiverRoomChat by lazy { recvUid + auth.currentUser?.uid }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = recvUserName

        binding.rvPrivateMessage.layoutManager = LinearLayoutManager(this)
        binding.rvPrivateMessage.adapter = privateMessageAdapter

        dbRef.child("chats").child(senderRoomChat).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listPrivateMessage.clear()
                    for (snap in snapshot.children) {
                        val privateMessage = snap.getValue(PrivateMessage::class.java)
                        privateMessage?.let {
                            listPrivateMessage.add(it)
                        }
                    }
                    privateMessageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )

        binding.btnKirim.setOnClickListener {
            val pesan = binding.etInputPesan.text.toString()
            val privateMessage = PrivateMessage(pesan, auth.currentUser?.uid.toString())

            val senderRoom = dbRef.child("chats").child(senderRoomChat)
            val receiverRoom = dbRef.child("chats").child(receiverRoomChat)

            senderRoom.push().setValue(privateMessage).addOnCompleteListener {
                when {
                    it.isSuccessful -> receiverRoom.push().setValue(privateMessage)
                }
            }

            binding.etInputPesan.setText("")

        }

    }
}