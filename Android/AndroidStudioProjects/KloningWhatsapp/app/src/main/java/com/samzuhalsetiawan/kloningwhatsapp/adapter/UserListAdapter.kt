package com.samzuhalsetiawan.kloningwhatsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.kloningwhatsapp.PrivateChatActivity
import com.samzuhalsetiawan.kloningwhatsapp.R
import com.samzuhalsetiawan.kloningwhatsapp.databinding.UserListBinding
import com.samzuhalsetiawan.kloningwhatsapp.model.UserObject

class UserListAdapter(private val userList: ArrayList<UserObject>):
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = UserListBinding.bind(view)
        val tvName = binding.tvName
        val tvPhone = binding.tvPhone
        val llUserList = binding.llUserList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = userList[position].phoneName
        holder.tvPhone.text = userList[position].phoneNumber
        holder.llUserList.setOnClickListener {
            val intent = Intent(holder.llUserList.context, PrivateChatActivity::class.java)
            intent.putExtra(PrivateChatActivity.PHONE_NAME_EXTRA, userList[position].phoneName)
            intent.putExtra(PrivateChatActivity.PHONE_NUMBER_EXTRA, userList[position].phoneNumber)
            holder.llUserList.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = userList.size
}