package com.example.learnfundamental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnApply: Button
    private lateinit var btnToLatihanTextView: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Latihan Menggunakan Logcat
        Log.e("Main Activity", "Tes Log Error")

        btnApply = findViewById(R.id.btn_apply)

        btnApply.setOnClickListener {
            val intent = Intent(this, LatihanConstraintLayout::class.java)
            startActivity(intent)
        }

        btnToLatihanTextView = findViewById(R.id.btn_to_latihan_text_view)
        btnToLatihanTextView.setOnClickListener {
            val intent = Intent(this, LatihanTextView::class.java)
            startActivity(intent)
        }

    }
}