package com.samzuhalsetiawan.latihanchatapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.latihanchatapp.PrivateMessageActivity
import com.samzuhalsetiawan.latihanchatapp.R
import com.samzuhalsetiawan.latihanchatapp.databinding.ListUserBinding
import com.samzuhalsetiawan.latihanchatapp.model.User

class UserAdapter(private val listUser: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val context: Context, view: View): RecyclerView.ViewHolder(view) {
        private val binding = ListUserBinding.bind(view)
        val tvName = binding.tvNama
        val llListUser = binding.llListUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_user, parent, false)
        return ViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = listUser[position].username
        holder.llListUser.setOnClickListener {
            val pmIntent = Intent(holder.context, PrivateMessageActivity::class.java)
            pmIntent.putExtra(PrivateMessageActivity.EXTRA_USERNAME, listUser[position].username)
            pmIntent.putExtra(PrivateMessageActivity.EXTRA_RECEIVER_UID, listUser[position].uid)
            holder.context.startActivity(pmIntent)
        }
    }

    override fun getItemCount(): Int = listUser.size
}