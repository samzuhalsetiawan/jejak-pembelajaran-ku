package com.example.latihanarchitecturecomponent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanarchitecturecomponent.databinding.CardNoteListBinding
import com.example.latihanarchitecturecomponent.models.Note

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    class ViewHolder(val binding: CardNoteListBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var noteList: List<Note>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    fun getNoteAtPosition(index: Int): Note? {
        if (noteList.size <= index) return null
        return noteList[index]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardNoteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvNoteTitle.text = noteList[position].title
            tvNoteDesc.text = noteList[position].description
            tvNotePriority.text = noteList[position].priority.toString()
        }
    }

    override fun getItemCount(): Int = noteList.size
}