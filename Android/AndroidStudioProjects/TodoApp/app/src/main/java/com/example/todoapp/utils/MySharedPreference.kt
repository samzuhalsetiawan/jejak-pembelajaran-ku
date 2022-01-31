package com.example.todoapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.todoapp.MainActivity
import com.example.todoapp.data.ListTodoDiHari
import com.example.todoapp.models.Hari
import com.example.todoapp.models.TodoList

object MySharedPreference {

    private fun getSharedPreferenceHari(hari: Hari, context: Context): SharedPreferences {
        return context.getSharedPreferences("TODOS_${hari.name}", Context.MODE_PRIVATE)
    }

    private fun cleanUpDatabase() {
        ListTodoDiHari.senin.removeAll(ListTodoDiHari.senin)
        ListTodoDiHari.selasa.removeAll(ListTodoDiHari.selasa)
        ListTodoDiHari.rabu.removeAll(ListTodoDiHari.rabu)
        ListTodoDiHari.kamis.removeAll(ListTodoDiHari.kamis)
        ListTodoDiHari.jumat.removeAll(ListTodoDiHari.jumat)
        ListTodoDiHari.sabtu.removeAll(ListTodoDiHari.sabtu)
        ListTodoDiHari.minggu.removeAll(ListTodoDiHari.minggu)
    }

    private fun saveAllTodoTo(hari: Hari, context: Context, todoList: List<TodoList>) {
        val myPref = getSharedPreferenceHari(hari, context)
        val keys = myPref.all.keys
        myPref.edit {
            for (key in keys) remove(key)
            for (todo in todoList) {
                putBoolean(todo.title, todo.sudahDilakukan)
            }
            commit()
        }
    }

    fun saveAllTodoFromDatabaseToPref(context: Context) {
        saveAllTodoTo(Hari.SENIN, context, ListTodoDiHari.senin)
        saveAllTodoTo(Hari.SELASA, context, ListTodoDiHari.selasa)
        saveAllTodoTo(Hari.RABU, context, ListTodoDiHari.rabu)
        saveAllTodoTo(Hari.KAMIS, context, ListTodoDiHari.kamis)
        saveAllTodoTo(Hari.JUMAT, context, ListTodoDiHari.jumat)
        saveAllTodoTo(Hari.SABTU, context, ListTodoDiHari.sabtu)
        saveAllTodoTo(Hari.MINGGU, context, ListTodoDiHari.minggu)
    }

    fun addAllTodoFromPrefToDatabase(context: Context) {
        cleanUpDatabase()
        for (hari in Hari.values()) {
            val myPref = getSharedPreferenceHari(hari, context)
            val keys = myPref.all.keys
            for (key in keys) {
                val value = myPref.getBoolean(key, false)
                MainActivity.addTodoToDatabase(TodoList(hari, key, value))
            }
        }
    }

}