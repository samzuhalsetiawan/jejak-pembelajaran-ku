package com.samzuhalsetiawan.latihandiffutil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.latihandiffutil.databinding.NameItemBinding

class PersonAdapter: RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: NameItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCalback = object : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCalback)
    fun submitList(list: List<Person>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NameItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.tvName.text = currentItem.name
    }

    override fun getItemCount(): Int = differ.currentList.size
}