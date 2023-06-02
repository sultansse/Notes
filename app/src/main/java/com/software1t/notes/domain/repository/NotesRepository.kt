package com.software1t.notes.domain.repository

import androidx.lifecycle.LiveData
import com.software1t.notes.data.local.entities.NotesEntity

interface NotesRepository {

    fun getNote(noteId: Long): LiveData<NotesEntity>

    suspend fun insertNote(notesEntity: NotesEntity)

    suspend fun deleteNote(notesEntity: NotesEntity)

    suspend fun updateNote(notesEntity: NotesEntity)

    fun getAllNotes(): LiveData<List<NotesEntity>>

    suspend fun insertAllNotes(notesEntities: List<NotesEntity>)

    suspend fun deleteAllNotes()
}