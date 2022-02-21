package com.samzuhalsetiawan.latihanchatapp2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp2.R
import com.samzuhalsetiawan.latihanchatapp2.databinding.ReceiverMessageBinding
import com.samzuhalsetiawan.latihanchatapp2.databinding.SenderMessageBinding
import com.samzuhalsetiawan.latihanchatapp2.model.PrivateMessage

class PrivateMessageAdapter(private val listMassage: ArrayList<PrivateMessage>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val SENDER_VIEW_TYPE = 1
        private const val RECEIVER_VIEW_TYPE = 2
    }

    private val auth by lazy { Firebase.auth }

    inner class SenderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = SenderMessageBinding.bind(view)
        val tvSendMessage = binding.tvSenderMessage
    }

    inner class ReceiverViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ReceiverMessageBinding.bind(view)
        val tvReceiverMessage = binding.tvReceiverMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SENDER_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.sender_message, parent, false)
                SenderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.receiver_message, parent, false)
                ReceiverViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (listMassage[position].senderUid) {
            auth.currentUser?.uid -> {
                val sendHolder = holder as SenderViewHolder
                sendHolder.tvSendMessage.text = listMassage[position].pesan
            }
            else -> {
                val recvHolder = holder as ReceiverViewHolder
                recvHolder.tvReceiverMessage.text = listMassage[position].pesan
            }
        }
    }

    override fun getItemCount(): Int = listMassage.size

    override fun getItemViewType(position: Int): Int {
        return when (listMassage[position].senderUid) {
            auth.currentUser?.uid -> SENDER_VIEW_TYPE
            else -> RECEIVER_VIEW_TYPE
        }
    }

}