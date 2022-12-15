package com.example.tes_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tes_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnPlay.setOnClickListener {
            startService(Intent(this, MyService::class.java))
        }
        binding.btnStop.setOnClickListener {
            stopService(Intent(this, MyService::class.java))
        }
    }
}