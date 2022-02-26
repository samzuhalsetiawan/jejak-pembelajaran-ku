package com.samzuhalsetiawan.latihansqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.samzuhalsetiawan.latihansqlite.adapter.UserAdapter
import com.samzuhalsetiawan.latihansqlite.databinding.ActivitySeeDataBinding

class SeeDataActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySeeDataBinding.inflate(layoutInflater) }
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val users = AddDataActivity.myUserDatabase.getEveryone()
        userAdapter = UserAdapter(users)
        binding.rvSeeData.layoutManager = LinearLayoutManager(this)
        binding.rvSeeData.adapter = userAdapter
    }
}