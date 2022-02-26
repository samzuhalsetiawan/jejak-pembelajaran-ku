package com.samzuhalsetiawan.kloningwhatsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.kloningwhatsapp.R
import com.samzuhalsetiawan.kloningwhatsapp.databinding.AnotherBubbleChatBinding
import com.samzuhalsetiawan.kloningwhatsapp.databinding.UserBubbleChatBinding
import com.samzuhalsetiawan.kloningwhatsapp.model.Message

class PrivateChatAdapter(private val anotherPhoneNumber: String, private val userChats: List<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val USER_VH_TYPE = 1
        private const val ANOTHER_VH_TYPE = 2
    }

    inner class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = UserBubbleChatBinding.bind(view)
        val tvUserChat = binding.tvUserChat
    }

    inner class AnotherViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = AnotherBubbleChatBinding.bind(view)
        val tvAnotherChat = binding.tvAnotherChat
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ANOTHER_VH_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.another_bubble_chat, parent, false)
                AnotherViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_bubble_chat, parent, false)
                UserViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (userChats[position].senderPhoneNumber) {
            anotherPhoneNumber -> {
                val viewHolder = holder as AnotherViewHolder
                viewHolder.tvAnotherChat.text = userChats[position].message
            }
            else -> {
                val viewHolder = holder as UserViewHolder
                viewHolder.tvUserChat.text = userChats[position].message
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (userChats[position].senderPhoneNumber) {
            anotherPhoneNumber -> ANOTHER_VH_TYPE
            else -> USER_VH_TYPE
        }
    }

    override fun getItemCount(): Int = userChats.size
}