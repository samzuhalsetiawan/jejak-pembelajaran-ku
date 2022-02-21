package com.samzuhalsetiawan.latihanchatapp2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.latihanchatapp2.PrivateMessageActivity
import com.samzuhalsetiawan.latihanchatapp2.R
import com.samzuhalsetiawan.latihanchatapp2.databinding.UserListBinding
import com.samzuhalsetiawan.latihanchatapp2.model.User

class UserAdapter(private val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val context: Context, view: View): RecyclerView.ViewHolder(view) {
        private val binding = UserListBinding.bind(view)
        val tvUsername = binding.tvUsername
        val cvUserList = binding.cvUserList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_list, parent, false)
        return ViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUsername.text = userList[position].username
        holder.cvUserList.setOnClickListener {
            val privateMessageIntent = Intent(holder.context, PrivateMessageActivity::class.java)
            privateMessageIntent.putExtra(PrivateMessageActivity.EXTRA_RECEIVER_USERNAME, userList[position].username)
            privateMessageIntent.putExtra(PrivateMessageActivity.EXTRA_RECEIVER_UID, userList[position].uid)
            holder.context.startActivity(privateMessageIntent)
        }
    }

    override fun getItemCount(): Int = userList.size
}