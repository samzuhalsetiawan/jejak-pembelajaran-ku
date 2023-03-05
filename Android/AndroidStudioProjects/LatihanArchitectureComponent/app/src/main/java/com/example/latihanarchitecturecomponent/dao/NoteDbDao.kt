package com.example.latihanarchitecturecomponent.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.latihanarchitecturecomponent.models.Note

@Dao
interface NoteDbDao {

    @Insert(entity = Note::class)
    suspend fun insertNote(note: Note)

    @Insert(entity = Note::class)
    suspend fun insertNoteMany(vararg note: Note)

    @Update(entity = Note::class)
    suspend fun updateNote(note: Note)

    @Delete(entity = Note::class)
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNote()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNote(): LiveData<List<Note>>
}