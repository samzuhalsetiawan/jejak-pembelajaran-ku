package com.example.todoapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.todoapp.adapter.TodoWeeklyAdapter
import com.example.todoapp.data.ListTodoDiHari
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.databinding.AddNewTodoBinding
import com.example.todoapp.databinding.FragmentTodoInOneWeekBinding
import com.example.todoapp.fragment.TodoInOneWeekFragment
import com.example.todoapp.models.Hari
import com.example.todoapp.models.TodoList
import com.example.todoapp.utils.MySharedPreference

class MainActivity : AppCompatActivity() {

//    TODO: Ubah nama aplikasi
//    TODO: Ubah title action bar
//    TODO: Ubah Icon
//    TODO: Hilangkan warning (i.e: string resources)

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val drawertoggle by lazy { ActionBarDrawerToggle(this, binding.dlMainActivity, R.string.open_drawer, R.string.close_drawer) }
    private val todoInOneWeekFragment = TodoInOneWeekFragment()

    companion object {
        fun addTodoToDatabase(todoList: TodoList, toastSucces: Toast? = null) {
            when (todoList.hari) {
                Hari.SENIN -> {
                    ListTodoDiHari.senin.add(todoList)
                    TodoWeeklyAdapter.notifyItemInsertedToAdapterOf(Hari.SENIN)
                    toastSucces?.show()
                }
                Hari.SELASA -> {
                    ListTodoDiHari.selasa.add(todoList)
                    TodoWeeklyAdapter.notifyItemInsertedToAdapterOf(Hari.SELASA)
                    toastSucces?.show()
                }
                Hari.RABU -> {
                    ListTodoDiHari.rabu.add(todoList)
                    TodoWeeklyAdapter.notifyItemInsertedToAdapterOf(Hari.RABU)
                    toastSucces?.show()
                }
                Hari.KAMIS -> {
                    ListTodoDiHari.kamis.add(todoList)
                    TodoWeeklyAdapter.notifyItemInsertedToAdapterOf(Hari.KAMIS)
                    toastSucces?.show()
                }
                Hari.JUMAT -> {
                    ListTodoDiHari.jumat.add(todoList)
                    TodoWeeklyAdapter.notifyItemInsertedToAdapterOf(Hari.JUMAT)
                    toastSucces?.show()
                }
                Hari.SABTU -> {
                    ListTodoDiHari.sabtu.add(todoList)
                    TodoWeeklyAdapter.notifyItemInsertedToAdapterOf(Hari.SABTU)
                    toastSucces?.show()
                }
                Hari.MINGGU -> {
                    ListTodoDiHari.minggu.add(todoList)
                    TodoWeeklyAdapter.notifyItemInsertedToAdapterOf(Hari.MINGGU)
                    toastSucces?.show()
                }
            }
        }

        fun removeTodoFromDatabase(hari: Hari, todo: TodoList, toastSucces: Toast? = null) {
            when (hari) {
                Hari.SENIN -> {
                    val iterator = ListTodoDiHari.senin.listIterator()
                    while (iterator.hasNext()) {
                        val todoList = iterator.next()
                        if (todoList == todo) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, ListTodoDiHari.senin.indexOf(todoList))
                            iterator.remove()
                            toastSucces?.show()
                            break
                        }
                    }
                }
                Hari.SELASA -> {
                    val iterator = ListTodoDiHari.selasa.listIterator()
                    while (iterator.hasNext()) {
                        val todoList = iterator.next()
                        if (todoList == todo) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, ListTodoDiHari.selasa.indexOf(todoList))
                            iterator.remove()
                            toastSucces?.show()
                            break
                        }
                    }
                }
                Hari.RABU -> {
                    val iterator = ListTodoDiHari.rabu.listIterator()
                    while (iterator.hasNext()) {
                        val todoList = iterator.next()
                        if (todoList == todo) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, ListTodoDiHari.rabu.indexOf(todoList))
                            iterator.remove()
                            toastSucces?.show()
                            break
                        }
                    }
                }
                Hari.KAMIS -> {
                    val iterator = ListTodoDiHari.kamis.listIterator()
                    while (iterator.hasNext()) {
                        val todoList = iterator.next()
                        if (todoList == todo) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, ListTodoDiHari.kamis.indexOf(todoList))
                            iterator.remove()
                            toastSucces?.show()
                            break
                        }
                    }
                }
                Hari.JUMAT -> {
                    val iterator = ListTodoDiHari.jumat.listIterator()
                    while (iterator.hasNext()) {
                        val todoList = iterator.next()
                        if (todoList == todo) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, ListTodoDiHari.jumat.indexOf(todoList))
                            iterator.remove()
                            toastSucces?.show()
                            break
                        }
                    }
                }
                Hari.SABTU -> {
                    val iterator = ListTodoDiHari.sabtu.listIterator()
                    while (iterator.hasNext()) {
                        val todoList = iterator.next()
                        if (todoList == todo) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, ListTodoDiHari.sabtu.indexOf(todoList))
                            iterator.remove()
                            toastSucces?.show()
                            break
                        }
                    }
                }
                Hari.MINGGU -> {
                    val iterator = ListTodoDiHari.minggu.listIterator()
                    while (iterator.hasNext()) {
                        val todoList = iterator.next()
                        if (todoList == todo) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, ListTodoDiHari.minggu.indexOf(todoList))
                            iterator.remove()
                            toastSucces?.show()
                            break
                        }
                    }
                }
            }
        }
        fun changeTodoFromDatabase(hari: Hari, oldTitle: String, newTitle: String, toastSucces: Toast? = null) {
            when (hari) {
                Hari.SENIN -> {
                    ListTodoDiHari.senin.forEachIndexed { index, todoList ->
                        if (todoList.title == oldTitle) {
                            TodoWeeklyAdapter.notifyItemChangedToAdapterOf(hari, index)
                            ListTodoDiHari.senin[index].title = newTitle
                            toastSucces?.show()
                            return@forEachIndexed
                        }
                    }
                }
                Hari.SELASA -> {
                    ListTodoDiHari.selasa.forEachIndexed { index, todoList ->
                        if (todoList.title == oldTitle) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, index)
                            ListTodoDiHari.selasa[index].title = newTitle
                            toastSucces?.show()
                            return@forEachIndexed
                        }
                    }
                }
                Hari.RABU -> {
                    ListTodoDiHari.rabu.forEachIndexed { index, todoList ->
                        if (todoList.title == oldTitle) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, index)
                            ListTodoDiHari.rabu[index].title = newTitle
                            toastSucces?.show()
                            return@forEachIndexed
                        }
                    }
                }
                Hari.KAMIS -> {
                    ListTodoDiHari.kamis.forEachIndexed { index, todoList ->
                        if (todoList.title == oldTitle) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, index)
                            ListTodoDiHari.kamis[index].title = newTitle
                            toastSucces?.show()
                            return@forEachIndexed
                        }
                    }
                }
                Hari.JUMAT -> {
                    ListTodoDiHari.jumat.forEachIndexed { index, todoList ->
                        if (todoList.title == oldTitle) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, index)
                            ListTodoDiHari.jumat[index].title = newTitle
                            toastSucces?.show()
                            return@forEachIndexed
                        }
                    }
                }
                Hari.SABTU -> {
                    ListTodoDiHari.sabtu.forEachIndexed { index, todoList ->
                        if (todoList.title == oldTitle) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, index)
                            ListTodoDiHari.sabtu[index].title = newTitle
                            toastSucces?.show()
                            return@forEachIndexed
                        }
                    }
                }
                Hari.MINGGU -> {
                    ListTodoDiHari.minggu.forEachIndexed { index, todoList ->
                        if (todoList.title == oldTitle) {
                            TodoWeeklyAdapter.notifyItemRemovedToAdapterOf(hari, index)
                            ListTodoDiHari.minggu[index].title = newTitle
                            toastSucces?.show()
                            return@forEachIndexed
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MySharedPreference.addAllTodoFromPrefToDatabase(applicationContext)

        binding.dlMainActivity.addDrawerListener(drawertoggle)
        drawertoggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.nvMainActivity.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itNavMenuOption1 -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.itNavMenuOption2 -> {
                    println("Option 2")
                    true
                }
                else -> {
                    println("False on listener navigation item selected")
                    false
                }
            }
        }

//        TODO: Pindah fragmen harus dari drawer ?
        goToFragment(todoInOneWeekFragment)
    }

    override fun onStop() {
        super.onStop()
        Log.e("DEBUG", "On Stop")
        MySharedPreference.saveAllTodoFromDatabaseToPref(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawertoggle.onOptionsItemSelected(item)) return true
        return when (item.itemId) {
            R.id.menuItemAddNewTodo -> {
                addNewTodo()
                true
            } else -> false
        }
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainerFragmentMainActivity,fragment)
            commit()
        }
    }

    private fun addNewTodo() {
        AlertDialog.Builder(this).apply {
            setTitle("Tambahkan Todo Baru")
            val dialogView = layoutInflater.inflate(R.layout.add_new_todo, binding.root, false)
            setView(dialogView)
            val addNewTodoBinding = AddNewTodoBinding.bind(dialogView)
            addNewTodoBinding.spinnerNamaHari.setSelection(todoInOneWeekFragment.currentTab)
            setCancelable(false)
            setPositiveButton("Tambahkan") { _: DialogInterface, _: Int ->
                val todo = addNewTodoBinding.etAddNewTodo.text.toString()
                val selectedPosition = addNewTodoBinding.spinnerNamaHari.selectedItemPosition
                addTodoToDatabase(TodoList(Hari.values()[selectedPosition], todo), Toast.makeText(this@MainActivity, "Berhasil ditambahkan", Toast.LENGTH_SHORT))
            }
            setNegativeButton("kembali") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.cancel()
            }
            create()
        }.show()
    }

}