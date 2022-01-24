package com.example.learnfundamental2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnfundamental2.R
import com.example.learnfundamental2.dummydata.TodoList

class TodoListAdapter(private val todoList: List<TodoList>): RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    inner class TodoListViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_todo_list, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.tvTodoList).text = todoList[position].name
            findViewById<CheckBox>(R.id.cbTodoList).isChecked = todoList[position].isChecked
        }
    }

    override fun getItemCount(): Int = todoList.size
}