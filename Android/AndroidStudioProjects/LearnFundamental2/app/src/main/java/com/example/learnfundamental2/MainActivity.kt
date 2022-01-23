package com.example.learnfundamental2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToLatihanImplicitIntent.setOnClickListener {
            startActivity(Intent(this, LatihanImplicitIntentActivity::class.java))
        }
        binding.btnToLatihanImplicitIntent2.setOnClickListener {
            startActivity(Intent(this, LatihanImplicitIntent2Activity::class.java))
        }
        binding.btnToLatihanImplicitIntent3.setOnClickListener {
            startActivity(Intent(this, LatihanImplicitIntent3Activity::class.java))
        }
        binding.btnToLatihanMenuItem.setOnClickListener {
            startActivity(Intent(this, LatihanMenuItemActivity::class.java))
        }
    }
}