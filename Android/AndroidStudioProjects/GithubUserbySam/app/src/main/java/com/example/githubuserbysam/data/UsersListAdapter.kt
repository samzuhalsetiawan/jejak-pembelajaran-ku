package com.example.githubuserbysam.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserbysam.databinding.UserCardBinding
import com.example.githubuserbysam.models.User

class UsersListAdapter : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: UserCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val user = listOfUser[position]
            binding.apply {
                Glide.with(root)
                    .load(user.avatarUrl)
                    .into(ivProfilePicture)

                tvUsername.text = user.userName
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listOfUser: List<User>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = listOfUser.size
}