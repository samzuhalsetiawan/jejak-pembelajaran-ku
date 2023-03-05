package com.example.latihanarchitecturecomponent.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.latihanarchitecturecomponent.database.NoteDB
import com.example.latihanarchitecturecomponent.models.Note

class NoteRepository(application: Application) {
    private val noteDao by lazy { NoteDB.getInstance(application).noteDbDao() }
    val allNote: LiveData<List<Note>> by lazy { noteDao.getAllNote() }

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun insertNoteMany(vararg note: Note) {
        noteDao.insertNoteMany(*note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun deleteAllNote() {
        noteDao.deleteAllNote()
    }

}