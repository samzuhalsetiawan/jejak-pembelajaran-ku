package com.example.learnfundamental2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.databinding.ActivityLatihanIntentService2Binding
import com.example.learnfundamental2.services.MyIntentService2

class LatihanIntentService2Activity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanIntentService2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        percobaan1()

//        percobaan2()

    }

    private fun percobaan1() {
        val intentService = Intent(this, MyIntentService2::class.java)

        binding.btnStartService.setOnClickListener {
            MyIntentService2.isRunning = true
            startService(intentService)
            println("=== Service start ======== isRunning: ${MyIntentService2.isRunning}")
            binding.tvServiceStatus.text = "Service Started"
        }

        binding.btnStopService.setOnClickListener {
            MyIntentService2.isRunning = false
            stopService(intentService)
            println("=== Service stop ======== isRunning: ${MyIntentService2.isRunning}")
            binding.tvServiceStatus.text = "Service Stoped"
        }
    }

    private fun percobaan2() {

        binding.btnStartService.setOnClickListener {
            Intent(this, MyIntentService2::class.java).also {
                MyIntentService2.isRunning = true
                startService(it)
                println("=== Service start ======== isRunning: ${MyIntentService2.isRunning}")
                binding.tvServiceStatus.text = "Service Started"

                binding.btnStopService.setOnClickListener { _ ->
                    MyIntentService2.isRunning = false
                    stopService(it)
                    println("=== Service stop ======== isRunning: ${MyIntentService2.isRunning}")
                    binding.tvServiceStatus.text = "Service Stoped"
                }
            }
        }

    }

}