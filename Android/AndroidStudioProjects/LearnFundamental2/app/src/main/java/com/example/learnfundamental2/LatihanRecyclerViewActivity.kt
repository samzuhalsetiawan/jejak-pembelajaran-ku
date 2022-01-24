package com.example.learnfundamental2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnfundamental2.adapter.TodoListAdapter
import com.example.learnfundamental2.databinding.ActivityLatihanRecyclerViewBinding
import com.example.learnfundamental2.dummydata.TodoList

class LatihanRecyclerViewActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanRecyclerViewBinding.inflate(layoutInflater) }
    private val recyclerView by lazy { binding.rvTodoList }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val todoList = mutableListOf(
            TodoList("Olahraga"),
            TodoList("Mengerjakan PR"),
            TodoList("Belajar Kotlin"),
            TodoList("Belajar Jetpack Compose"),
            TodoList("Membuat Konten PeerJS"),
            TodoList("Makan", true)
        )

        val adapter = TodoListAdapter(todoList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnAddTodo.setOnClickListener {
            val todo = binding.edTodoList.text.toString()
            todoList.add(TodoList(todo))
            adapter.notifyItemInserted(todoList.lastIndex)
        }

    }
}