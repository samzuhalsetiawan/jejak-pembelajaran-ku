package com.example.learnfundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class LatihanConstraintLayout : AppCompatActivity() {

    private lateinit var btnPindah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_constraint_layout)

        btnPindah = findViewById(R.id.btn_pindah1)
        btnPindah.setOnClickListener {
            Toast.makeText(this.applicationContext, "Hello World", Toast.LENGTH_SHORT).show()
        }

    }
}