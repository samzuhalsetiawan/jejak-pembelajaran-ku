package com.samzuhalsetiawan.latihanretrofit2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.samzuhalsetiawan.latihanretrofit2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val todoListAdapter by lazy { TodoListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeAdapterRV()

        lifecycleScope.launchWhenCreated {
            binding.pbLoading.visibility = View.VISIBLE
            val client = RetrofitInstance.todoApi.getAllTodos()
            client.enqueue( object : Callback<List<Todo>>{
                override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                    response.body()?.let {
                        todoListAdapter.submitList(it)
                        binding.pbLoading.visibility = View.GONE
                        Log.e(TAG, it.toList()[0].toString())
                    }
                }

                override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                    Log.e(TAG, "Failure: ${t.message}")
                    Toast.makeText(this@MainActivity, "Gagal Menampilkan Data", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }

            })
        }
    }

    private fun initializeAdapterRV() {
        binding.rvTodoList.apply {
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

}