package com.example.storyapp.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.data.models.Story
import com.example.storyapp.databinding.CardUserStoryBinding
import com.google.android.material.card.MaterialCardView

class UserStoryAdapter(private val onCardClicked: OnStoryCardClicked? = null) : RecyclerView.Adapter<UserStoryAdapter.ViewHolder>() {

    fun interface OnStoryCardClicked {
        fun onStoryCardClicked(story: Story, card: MaterialCardView)
    }

    inner class ViewHolder(binding: CardUserStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivStoryImage = binding.ivStoryImage
        val tvStoryTitle = binding.tvStoryTitle
        val tvStoryDate = binding.tvStoryDate
        val tvStoryDescription = binding.tvStoryDescription
        val mcvStoryCard = binding.mcvStoryCard
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listOfStories: List<Story>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardUserStoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.card_user_story, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = listOfStories[position]

        Glide.with(holder.ivStoryImage)
            .load(story.photoUrl)
            .into(holder.ivStoryImage)

        holder.apply {
            tvStoryTitle.text = story.name
            tvStoryDate.text = story.createdAt
            tvStoryDescription.text = story.description
            mcvStoryCard.setOnClickListener { onCardClicked?.onStoryCardClicked(story, mcvStoryCard) }
        }
    }

    override fun getItemCount(): Int = listOfStories.size
}