package com.example.githubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.models.User
import com.example.githubuser.databinding.UserCardBinding
import com.example.githubuser.interfaces.IUserCardClickEventHandler

class UserListAdapter(private val clickHandler: IUserCardClickEventHandler) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: UserCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = binding.bindData(user)

        private fun UserCardBinding.bindData(user: User) {
            Glide.with(binding.root).load(user.avatarUrl).into(ivUserProfilePicture)
            tvUsername.text = user.login
            tvHtmlUrl.text = user.htmlUrl
            cvUserCard.setOnClickListener { clickHandler.onCardClickListener(cvUserCard, user) }
            cbFavoriteButton.isChecked = user.isFavorite
            cbFavoriteButton.setOnClickListener { cbFavoriteButtonClickHandler(user) }
        }

        private fun UserCardBinding.cbFavoriteButtonClickHandler(user: User) {
            user.isFavorite = !user.isFavorite
            cbFavoriteButton.isChecked = user.isFavorite
            if (user.isFavorite) clickHandler.onFavoriteIconCheckedListener(cbFavoriteButton, user)
            else clickHandler.onFavoriteIconUncheckedListener(cbFavoriteButton, user)
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listOfUser: List<User>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfUser[position])
    }

    override fun getItemCount(): Int = listOfUser.size
}