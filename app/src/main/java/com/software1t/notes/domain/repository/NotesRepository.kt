package com.software1t.notes.domain.repository

import androidx.lifecycle.LiveData
import com.software1t.notes.data.local.entities.NotesEntity

interface NotesRepository {

    fun getNoteById(noteId: Long): LiveData<NotesEntity>

    suspend fun deleteNoteById(noteId: Long)

    suspend fun insertNote(notesEntity: NotesEntity)

    suspend fun deleteNote(notesEntity: NotesEntity)

    suspend fun updateNote(notesEntity: NotesEntity)

    fun getAllNotesAsc(): LiveData<List<NotesEntity>>

    fun getAllNotesDesc(): LiveData<List<NotesEntity>>

    suspend fun insertAllNotes(notesEntities: List<NotesEntity>)

    suspend fun deleteAllNotes()
}