package com.example.learnfundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LatihanTextView : AppCompatActivity() {

    private lateinit var tvCounter: TextView
    private lateinit var btnCounter: Button

    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_text_view)

        tvCounter = findViewById(R.id.tv_counter)
        btnCounter = findViewById(R.id.btn_counter)

        btnCounter.setOnClickListener {
            counter++
            tvCounter.text = counter.toString()
        }

    }
}