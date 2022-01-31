package com.example.todoapp.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.AddNewTodoBinding
import com.example.todoapp.databinding.RecyclerViewTodoBinding
import com.example.todoapp.models.Hari
import com.example.todoapp.models.TodoList

class TodoListAdapter(private val todoList: List<TodoList>) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerViewTodoBinding.bind(view)
        val tvTitle = binding.tvRvTodo
        val btnHapus = binding.btnRvTodoHapus
        val btnEdit = binding.btnRvTodoEdit
        val cbSudahDilakukan = binding.cbRvTodo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = todoList[position].title
        holder.cbSudahDilakukan.isChecked = todoList[position].sudahDilakukan
        holder.cbSudahDilakukan.setOnCheckedChangeListener { _, isChecked ->
            todoList[position].sudahDilakukan = isChecked
        }
        holder.btnEdit.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                val layoutInflater = LayoutInflater.from(holder.itemView.context)
                val dialogView = layoutInflater.inflate(R.layout.add_new_todo, holder.itemView.parent as ViewGroup, false)
                val binding = AddNewTodoBinding.bind(dialogView)
                setTitle("Edit Todo")
                setView(dialogView)
                setCancelable(false)
                binding.etAddNewTodo.text.apply { replace(0, length, todoList[position].title) }
                binding.spinnerNamaHari.setSelection(Hari.values().indexOf(todoList[position].hari))
                setPositiveButton("Ubah"){ _: DialogInterface, _: Int ->
                    val oldDay = todoList[position].hari
                    val newDay = Hari.values()[binding.spinnerNamaHari.selectedItemPosition]
                    val oldTitle = todoList[position].title
                    val newTitle = binding.etAddNewTodo.text.toString()
                    if (oldDay != newDay) {
                        MainActivity.addTodoToDatabase(TodoList(newDay, newTitle))
                        MainActivity.removeTodoFromDatabase(oldDay, TodoList(oldDay, oldTitle))
                    } else {
                        if (oldTitle != newTitle)
                            MainActivity.changeTodoFromDatabase(oldDay, oldTitle, newTitle)
                    }
                    Toast.makeText(holder.itemView.context, "Berhasil diubah", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Kembali") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.cancel()
                }
                create()
            }.show()
        }
        holder.btnHapus.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                val hari = todoList[position].hari
                val title = todoList[position].title
                val toastSuccesDelete = Toast.makeText(holder.itemView.context, "Berhasil dihapus", Toast.LENGTH_SHORT)
                setTitle("Hapus Todo")
                setMessage("Yakin ingin menghapus todo: $title ?")
                setPositiveButton("Yakin") { _: DialogInterface, _: Int ->
                    MainActivity.removeTodoFromDatabase(hari, TodoList(hari,title), toastSuccesDelete)
                }
                setNegativeButton("Batal") { dialogInterface: DialogInterface, _: Int -> dialogInterface.cancel()}
                create()
            }.show()
        }
    }

    override fun getItemCount(): Int = todoList.size
}