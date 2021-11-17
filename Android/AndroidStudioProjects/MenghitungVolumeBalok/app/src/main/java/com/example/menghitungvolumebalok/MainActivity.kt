package com.example.menghitungvolumebalok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editLength: EditText
    private lateinit var editHeight: EditText
    private lateinit var editWidth: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvHasil: TextView

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editHeight = findViewById(R.id.edit_height)
        editWidth = findViewById(R.id.edit_width)
        editLength = findViewById(R.id.edit_length)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvHasil = findViewById(R.id.tv_hasil)

        btnCalculate.setOnClickListener(this)
        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            tvHasil.text = result
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvHasil.text.toString())
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_calculate) {
            val inputLength = editLength.text.toString().trim()
            val inputWidth = editWidth.text.toString().trim()
            val inputHeight = editHeight.text.toString().trim()

            var isEmpty = false

            if (inputLength.isEmpty()) {
                editLength.error = "Tidak Boleh Kosong"
                isEmpty = true
            }
            if (inputHeight.isEmpty()) {
                editHeight.error = "Tidak Boleh Kosong"
                isEmpty = true
            }
            if (inputWidth.isEmpty()) {
                editWidth.error = "Tidak Boleh Kosong"
                isEmpty = true
            }

            if (!isEmpty) {
                val result = inputHeight.toDouble() * inputLength.toDouble() * inputWidth.toDouble()
                tvHasil.text = result.toString()
            }

        }
    }
}