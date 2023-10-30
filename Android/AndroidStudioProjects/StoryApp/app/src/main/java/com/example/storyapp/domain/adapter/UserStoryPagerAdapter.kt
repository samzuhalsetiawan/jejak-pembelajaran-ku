package com.example.storyapp.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.data.models.Story
import com.example.storyapp.data.source.local.entity.StoryEntity
import com.example.storyapp.data.source.local.entity.toStory
import com.example.storyapp.databinding.CardUserStoryBinding

class UserStoryPagerAdapter(private val onCardClicked: UserStoryAdapter.OnStoryCardClicked? = null): PagingDataAdapter<Story, UserStoryPagerAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(binding: CardUserStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivStoryImage = binding.ivStoryImage
        val tvStoryTitle = binding.tvStoryTitle
        val tvStoryDate = binding.tvStoryDate
        val tvStoryDescription = binding.tvStoryDescription
        val mcvStoryCard = binding.mcvStoryCard
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position) ?: return

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardUserStoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.card_user_story, parent, false)
            )
        )
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }

}