package com.samzuhalsetiawan.latihanchatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.latihanchatapp.R
import com.samzuhalsetiawan.latihanchatapp.databinding.ListUserBinding
import com.samzuhalsetiawan.latihanchatapp.model.User

class UserAdapter(private val listUser: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ListUserBinding.bind(view)
        val tvName = binding.tvNama
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = listUser[position].username
    }

    override fun getItemCount(): Int = listUser.size
}