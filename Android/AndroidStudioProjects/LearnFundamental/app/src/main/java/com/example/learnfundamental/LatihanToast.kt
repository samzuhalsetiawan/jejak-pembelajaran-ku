package com.example.learnfundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class LatihanToast : AppCompatActivity() {

    private lateinit var btnShowDefaultToast: Button
    private lateinit var btnShowCostumToast: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_toast)

        btnShowDefaultToast = findViewById(R.id.btnShowDefaultToast)
        btnShowDefaultToast.setOnClickListener {
            Toast.makeText(this, "Hello I`m Toast", Toast.LENGTH_LONG).show()
        }

        btnShowCostumToast = findViewById(R.id.btnShowCostumToast)
        btnShowCostumToast.setOnClickListener {
            Toast(this).apply {
                duration = Toast.LENGTH_LONG
                view = layoutInflater.inflate(R.layout.costum_toast, findViewById(R.id.clCostumToast))
                show()
            }
        }

    }
}