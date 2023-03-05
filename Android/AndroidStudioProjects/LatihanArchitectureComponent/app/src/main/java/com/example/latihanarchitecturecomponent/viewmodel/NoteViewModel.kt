package com.example.latihanarchitecturecomponent.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.latihanarchitecturecomponent.models.Note
import com.example.latihanarchitecturecomponent.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository = NoteRepository(application)

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    }
    fun insertManyNote(vararg note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNoteMany(*note)
        }
    }
    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
        }
    }
    fun deleteAllNote() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteAllNote()
        }
    }
    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(note)
        }
    }
    fun getAllNote(): LiveData<List<Note>> = noteRepository.allNote
}