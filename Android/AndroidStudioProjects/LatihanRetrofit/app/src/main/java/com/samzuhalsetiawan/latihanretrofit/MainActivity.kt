package com.samzuhalsetiawan.latihanretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.samzuhalsetiawan.latihanretrofit.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "MAIN_ACTIVITY"

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val todoListAdapter by lazy { TodoListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvTodos.apply {
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        lifecycleScope.launchWhenCreated {
            binding.pbLoading.visibility = View.VISIBLE
            val response = try {
                RetrofitInstance.api.getAllTodo()
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Gagal menampilkan data", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "IOExeception: ${e.message}")
                binding.pbLoading.visibility = View.INVISIBLE
                return@launchWhenCreated
            } catch (e: HttpException) {
                Toast.makeText(this@MainActivity, "Gagal menampilkan data", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "HTTPException: ${e.message}")
                binding.pbLoading.visibility = View.INVISIBLE
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                todoListAdapter.todos = response.body()!!
                binding.pbLoading.visibility = View.INVISIBLE
                return@launchWhenCreated
            } else {
                Toast.makeText(this@MainActivity, "Gagal menampilkan data", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Response Not Expected: ${response.body()?.toList()}")
                binding.pbLoading.visibility = View.INVISIBLE
                return@launchWhenCreated
            }
        }

    }

}