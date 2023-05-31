package com.example.latihanlocaldatapersistence

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.latihanlocaldatapersistence.databinding.ActivityLatihanSaveIntoInternalBinding

class LatihanSaveIntoInternal : AppCompatActivity() {

    private val binding: ActivityLatihanSaveIntoInternalBinding by lazy { ActivityLatihanSaveIntoInternalBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener { saveIntoInternal() }
        binding.btnOpen.setOnClickListener { readFromInternal() }

    }

    private fun saveIntoInternal() {
        val namaFile = binding.etNamaFile.text.toString()
        val contentFile = binding.etContentFile.text.toString()

        openFileOutput(namaFile, MODE_PRIVATE).use {
            it.write(contentFile.toByteArray())
        }
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
    }

    private fun readFromInternal() {
        val namaFile = binding.etNamaFile.text.toString()

        val contentFile = openFileInput(namaFile).bufferedReader().useLines {
            it.fold("") { acc: String, newLine: String ->
                "$acc\n$newLine"
            }
        }
        binding.tvContentFile.text = contentFile
    }

}