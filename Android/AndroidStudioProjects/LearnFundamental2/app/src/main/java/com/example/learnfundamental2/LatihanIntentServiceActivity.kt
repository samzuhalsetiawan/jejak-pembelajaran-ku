package com.example.learnfundamental2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.databinding.ActivityLatihanIntentServiceBinding
import com.example.learnfundamental2.services.MyIntentService

class LatihanIntentServiceActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanIntentServiceBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnStartService.setOnClickListener {
            Intent(this, MyIntentService::class.java).also {
                startService(it)
                binding.tvServiceStatus.text = "Service Started"
            }
        }

        binding.btnStopService.setOnClickListener {
            MyIntentService.stopService()
            binding.tvServiceStatus.text = "Service Stoped"
        }

    }



}