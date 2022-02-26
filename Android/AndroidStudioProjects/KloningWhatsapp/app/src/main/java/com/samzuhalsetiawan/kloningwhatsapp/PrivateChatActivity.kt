package com.samzuhalsetiawan.kloningwhatsapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.samzuhalsetiawan.kloningwhatsapp.adapter.PrivateChatAdapter
import com.samzuhalsetiawan.kloningwhatsapp.databinding.ActivityPrivateChatBinding
import com.samzuhalsetiawan.kloningwhatsapp.model.Message

class PrivateChatActivity : AppCompatActivity() {

    companion object {
        const val PHONE_NAME_EXTRA = "PHONE_NAME_EXTRA"
        const val PHONE_NUMBER_EXTRA = "PHONE_NUMBER_EXTRA"
    }

    private val binding by lazy { ActivityPrivateChatBinding.inflate(layoutInflater) }
    private val auth by lazy { Firebase.auth }
    private val database by lazy { Firebase.database("https://kloning-whatsapp-d26a8-default-rtdb.asia-southeast1.firebasedatabase.app/") }
    private val dbRef by lazy { database.reference }
    private val userPrivateChats = mutableListOf<Message>()
    private val linearLayoutManager by lazy { LinearLayoutManager(this) }
    lateinit var privateChatAdapter: PrivateChatAdapter
    private val userPrivateMedias = mutableSetOf<Uri>()
    private val storage by lazy { Firebase.storage("gs://kloning-whatsapp-d26a8.appspot.com/") }
    private val storageRef by lazy { storage.reference }
    private val mediaLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        userPrivateMedias.addAll(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.getStringExtra(PHONE_NAME_EXTRA)?.let {
            supportActionBar?.title = it
        }

        val anotherPhoneNumber = intent.getStringExtra(PHONE_NUMBER_EXTRA) ?: return
        privateChatAdapter = PrivateChatAdapter(anotherPhoneNumber, userPrivateChats)
        binding.rvPrivateChat.layoutManager = linearLayoutManager
        binding.rvPrivateChat.adapter = privateChatAdapter

        getAllChatFromDatabase(anotherPhoneNumber, auth.currentUser?.uid)

        binding.btnMedia.setOnClickListener { chooseMedia() }
        binding.btnKirim.setOnClickListener { sendMessageToDatabase(anotherPhoneNumber) }

    }

    private fun chooseMedia() {
        mediaLauncher.launch("image/*")
    }

    private fun sendMessageToDatabase(anotherPhoneNumber: String) {
        if (userPrivateMedias.isNotEmpty()) uploadMediaToStorage()
        val pesan = binding.etInputMessage.text.toString()
        binding.etInputMessage.setText("")
        currentFocus?.let {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputMethodManager.isAcceptingText) {
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
        val message = Message(LoginActivity.myPhoneNumber, pesan)
        dbRef.child("users")
            .child(auth.currentUser?.uid!!)
            .child("chats")
            .child(anotherPhoneNumber)
            .push()
            .setValue(message)
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> {
                        dbRef.child("users").addListenerForSingleValueEvent(
                            object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (children in snapshot.children) {
                                        val phoneNumber = children.child("phoneNumber").getValue<String>()
                                        if (phoneNumber == anotherPhoneNumber) {
                                            children.child("chats")
                                                .child(LoginActivity.myPhoneNumber)
                                                .ref.push().setValue(message)
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }
                            }
                        )
                    }
                }
            }
    }

    private fun uploadMediaToStorage() {
        for (fileUri in userPrivateMedias) {
            storageRef.child("chats/").child(Math.random().toString()).putFile(fileUri).addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {
                    Log.e("TAG", "SUCCESS TO GET DOWNLOAD URL: $it")
                }
            }
        }
        userPrivateMedias.clear()
    }

    private fun getAllChatFromDatabase(anotherPhoneNumber: String, uid: String?) {
        uid ?: return
        dbRef.child("users")
            .child(auth.currentUser?.uid!!)
            .child("chats")
            .child(anotherPhoneNumber)
            .addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userPrivateChats.clear()
                    for (children in snapshot.children) {
                        val chat = children.getValue(Message::class.java) ?: continue
                        userPrivateChats.add(chat)
                    }
                    privateChatAdapter.notifyDataSetChanged()
                    linearLayoutManager.scrollToPosition(userPrivateChats.lastIndex)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }
}