package com.software1t.notes.domain.repository

import com.software1t.notes.data.local.NotesEntity

interface NotesRepository {

    fun getNoteById(noteId: Long): NotesEntity

    suspend fun insertNote(notesEntity: NotesEntity)

    suspend fun deleteNote(notesEntity: NotesEntity)

    suspend fun updateNote(notesEntity: NotesEntity)

    fun getAllNotes(): List<NotesEntity>

    suspend fun insertAllNotes(notesEntities: List<NotesEntity>)

    suspend fun deleteAllNotes()
}