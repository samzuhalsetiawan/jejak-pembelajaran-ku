package com.example.learnfundamental2

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnfundamental2.broadcastreceiver.AirplanemodeBroadcastReceiver
import com.example.learnfundamental2.databinding.ActivityLatihanBroadcastBinding

class LatihanBroadcastActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLatihanBroadcastBinding.inflate(layoutInflater) }

    private val broadcastReceiver = AirplanemodeBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(broadcastReceiver,it)
        }

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

}