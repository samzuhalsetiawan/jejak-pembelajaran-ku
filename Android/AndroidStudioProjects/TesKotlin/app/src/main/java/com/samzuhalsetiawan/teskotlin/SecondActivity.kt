package com.samzuhalsetiawan.teskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<Button>(R.id.btnSendResult).setOnClickListener {
            val intent = Intent().apply {
                putExtra("EXTRA_DATA", "Hello Dunia")
            }
            setResult(RESULT_OK, intent)
            finish()
        }

    }

}