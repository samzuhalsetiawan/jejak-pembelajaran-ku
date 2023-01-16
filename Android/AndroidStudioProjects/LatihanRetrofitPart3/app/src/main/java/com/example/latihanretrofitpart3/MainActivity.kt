package com.example.latihanretrofitpart3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanretrofitpart3.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MAIN_ACTIVITY"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: TodoRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            binding.pbTodo.visibility = View.VISIBLE
            val response = try {
                RetrofitInstance.todoApi.getTodos()
            } catch (err: IOException) {
                Log.e(TAG, "IOException")
                binding.pbTodo.visibility = View.GONE
                return@launchWhenCreated
            } catch (err: HttpException) {
                Log.e(TAG, "HttpException")
                binding.pbTodo.visibility = View.GONE
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                rvAdapter.todoList = response.body()!!
            } else {
                Log.e(TAG, "unexpected response")
            }
            binding.pbTodo.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        rvAdapter = TodoRvAdapter()
        binding.rvTodo.adapter = rvAdapter
        binding.rvTodo.layoutManager = LinearLayoutManager(this)
    }
}