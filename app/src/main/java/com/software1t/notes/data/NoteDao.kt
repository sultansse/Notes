package com.software1t.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNote(noteId: Long): LiveData<Note>
}
