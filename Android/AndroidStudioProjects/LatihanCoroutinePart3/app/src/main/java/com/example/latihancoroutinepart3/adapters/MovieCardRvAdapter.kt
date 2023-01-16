package com.example.latihancoroutinepart3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.latihancoroutinepart3.R
import com.example.latihancoroutinepart3.databinding.CardListFilmBinding
import com.example.latihancoroutinepart3.models.Movie

class MovieCardRvAdapter : RecyclerView.Adapter<MovieCardRvAdapter.ViewHolder>() {
    class ViewHolder(val binding: CardListFilmBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    private val listMoviesDiffer = AsyncListDiffer(this, differCallback)

    var listMovies: List<Movie>
        get() = listMoviesDiffer.currentList
        set(value) { listMoviesDiffer.submitList(value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardListFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvMovieTitle.text = listMovies[position].title
            tvMovieYear.text = root.context.getString(R.string.default_year)
            tvMovieDesc.text = listMovies[position].description
            Glide.with(this.root.context)
                .load(listMovies[position].image)
                .into(ivMovieCover)
        }
    }

    override fun getItemCount(): Int = listMovies.size
}