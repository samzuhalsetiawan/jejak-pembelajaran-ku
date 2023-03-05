package com.example.latihanarchitecturecomponent2.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.latihanarchitecturecomponent2.databinding.UserCardListBinding

class UserRvAdapter : RecyclerView.Adapter<UserRvAdapter.ViewHolder>() {
    class ViewHolder(val binding: UserCardListBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var allUsers: List<User>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(UserCardListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvUserName.text = allUsers[position].userName
            tvUserDesc.text = allUsers[position].description
            Glide.with(this.root)
                .load(allUsers[position].profilePicture)
                .centerCrop()
                .into(ivUserPicture)
        }
    }

    override fun getItemCount(): Int = allUsers.size
}