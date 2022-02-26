package com.samzuhalsetiawan.latihansqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samzuhalsetiawan.latihansqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.let {
            it.title = "Home"
        }

        binding.btnAddData.setOnClickListener { goToHalamanAddData() }
        binding.btnSeeData.setOnClickListener { goToHalamanSeeData() }

    }

    private fun goToHalamanSeeData() {
        startActivity(Intent(this, SeeDataActivity::class.java))
    }

    private fun goToHalamanAddData() {
        startActivity(Intent(this, AddDataActivity::class.java))
    }
}