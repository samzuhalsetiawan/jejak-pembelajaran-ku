package com.samzuhalsetiawan.latihanretrofit2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.samzuhalsetiawan.latihanretrofit2.databinding.ListTodoLayoutBinding

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListTodoLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCalback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCalback)

    fun submitList(list: List<Todo>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListTodoLayoutBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            tvTodoName.text = currentItem.title
            cbTodo.isChecked = currentItem.completed
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}