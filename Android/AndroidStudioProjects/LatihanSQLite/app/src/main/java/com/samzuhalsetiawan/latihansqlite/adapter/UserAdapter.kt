package com.samzuhalsetiawan.latihansqlite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.latihansqlite.R
import com.samzuhalsetiawan.latihansqlite.databinding.UserListBinding
import com.samzuhalsetiawan.latihansqlite.model.User

class UserAdapter(private val listUser: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = UserListBinding.bind(view)
        val tvNama = binding.tvNama
        val tvUmur = binding.tvUmur
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNama.text = listUser[position].name
        holder.tvUmur.text = listUser[position].age.toString()
    }

    override fun getItemCount(): Int = listUser.size
}