package com.example.latihanarchitecturecomponent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.latihanarchitecturecomponent.databinding.ActivityAddNoteBinding
import com.example.latihanarchitecturecomponent.models.Note
import com.example.latihanarchitecturecomponent.viewmodel.NoteViewModel

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.npPriority.minValue = 1
        binding.npPriority.maxValue = 10
        binding.tabAddNote.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_save_note -> {
                    saveNote()
                    true
                }
                else -> false
            }
        }
    }

    private fun saveNote() {
        val noteTitle = binding.etNoteTitle.text.toString()
        val noteDesc = binding.etNoteDesc.text.toString()
        val notePrior = binding.npPriority.value
        val data = Intent().apply {
            putExtra(MainActivity.EXTRA_TITLE, noteTitle)
            putExtra(MainActivity.EXTRA_DESCRIPTION, noteDesc)
            putExtra(MainActivity.EXTRA_PRIORITY, notePrior)
        }
        setResult(RESULT_OK, data)
        finish()
    }
}