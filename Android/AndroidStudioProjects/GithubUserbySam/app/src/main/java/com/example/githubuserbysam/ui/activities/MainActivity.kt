package com.example.githubuserbysam.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserbysam.data.GithubUserViewModel
import com.example.githubuserbysam.data.UsersListAdapter
import com.example.githubuserbysam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersListAdapter: UsersListAdapter
    private val githubUserViewModel: GithubUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainContentBinding = binding.incContentMain

        val listOfUser = githubUserViewModel.getUsersByName("Sam Zuhal")
        listOfUser.observe(this) { usersListAdapter.listOfUser = it }

        mainContentBinding.rvUsersList.apply {
            adapter = UsersListAdapter().also { usersListAdapter = it }
            layoutManager = LinearLayoutManager(this@MainActivity)
        }


    }


}