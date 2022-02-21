package com.samzuhalsetiawan.latihanchatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samzuhalsetiawan.latihanchatapp.R
import com.samzuhalsetiawan.latihanchatapp.databinding.ReceiverMsgLayoutBinding
import com.samzuhalsetiawan.latihanchatapp.databinding.SenderMsgLayoutBinding
import com.samzuhalsetiawan.latihanchatapp.model.Message

class PrivateMessageAdapter(private val listMessage: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val SENDER_HOLDER_TYPE = 1
        private const val RECEIVER_HOLDER_TYPE = 2
    }

    private val mAuth by lazy { Firebase.auth }

    inner class SenderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = SenderMsgLayoutBinding.bind(view)
        val tvSendMsg = binding.tvSenderMsg
    }

    inner class RecvViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ReceiverMsgLayoutBinding.bind(view)
        val tvRecvMsg = binding.tvRecvMsg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SENDER_HOLDER_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.sender_msg_layout, parent, false)
                SenderViewHolder(view)
            } else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.receiver_msg_layout, parent, false)
                RecvViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (listMessage[position].senderUid) {
            mAuth.currentUser?.uid -> {
                val senderHolder = holder as SenderViewHolder
                senderHolder.tvSendMsg.text = listMessage[position].pesan
            } else -> {
                val recvHolder = holder as RecvViewHolder
                recvHolder.tvRecvMsg.text = listMessage[position].pesan
            }
        }
    }

    override fun getItemCount(): Int = listMessage.size

    override fun getItemViewType(position: Int): Int {
        return when (listMessage[position].senderUid) {
            mAuth.currentUser?.uid -> SENDER_HOLDER_TYPE
            else -> RECEIVER_HOLDER_TYPE
        }
    }

}