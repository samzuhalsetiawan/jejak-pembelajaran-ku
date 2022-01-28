package com.example.learnfundamental2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.databinding.ActivityLatihanServiceBinding
import com.example.learnfundamental2.services.MyService

class LatihanServiceActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanServiceBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val myService = Intent(this, MyService::class.java)

        binding.btnStartService.setOnClickListener {
            startService(myService)
            binding.tvServiceStatus.text = "Service Started"
        }

        binding.btnStopService.setOnClickListener {
            stopService(myService)
            binding.tvServiceStatus.text = "Service Stoped"
        }

        binding.btnSend.setOnClickListener {
            val data = binding.etLatihanService.text.toString()
            myService.putExtra("EXTRA_DATA", data)
            startService(myService)
            binding.tvServiceStatus.text = "Service Started"
        }

    }
}