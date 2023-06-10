package com.software1t.notes.data.repository

import androidx.lifecycle.LiveData
import com.software1t.notes.data.local.NoteDao
import com.software1t.notes.data.local.NotesEntity
import com.software1t.notes.domain.repository.NotesRepository

class NotesRepositoryImpl(private val noteDao: NoteDao) : NotesRepository {

    override fun getNoteById(noteId: Long): LiveData<NotesEntity> {
        return noteDao.getNoteById(noteId)
    }

    override suspend fun deleteNoteById(noteId: Long) {
        noteDao.deleteNoteById(noteId)
    }

    override suspend fun insertNote(notesEntity: NotesEntity) {
        noteDao.insertNote(notesEntity)
    }

    override suspend fun deleteNote(notesEntity: NotesEntity) {
        noteDao.deleteNote(notesEntity)
    }

    override suspend fun updateNote(notesEntity: NotesEntity) {
        noteDao.updateNote(notesEntity)
    }

    override fun getAllNotes(): LiveData<List<NotesEntity>> {
        return noteDao.getAllNotes()
    }

    override suspend fun insertAllNotes(notesEntities: List<NotesEntity>) {
        noteDao.insertAllNotes(notesEntities)
    }

     override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}