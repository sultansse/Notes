package com.software1t.notes.domain.useсases

import com.software1t.notes.data.local.entities.NotesEntity
import com.software1t.notes.data.local.model.NoteTimestamp
import com.software1t.notes.domain.repository.NotesRepository

class SaveButtonClick(private val notesRepository: NotesRepository) { // Inject NotesRepository
    suspend fun execute(title: String, desc: String, noteId: Long) {
        val currentNote = notesRepository.getNote(noteId)

        val currentTime = System.currentTimeMillis()
        val noteTimestamp = NoteTimestamp(
            createdAt = currentNote.value?.noteTimestamp?.createdAt ?: currentTime,
            lastModifiedAt = currentNote.value?.noteTimestamp?.lastModifiedAt ?: currentTime
        )

        lateinit var note: NotesEntity
        if (noteId == -1L) {
            note = NotesEntity(title = title, description = desc, noteTimestamp = noteTimestamp)
            notesRepository.insertNote(note)
        } else {
            note = currentNote.value?.copy(title = title, description = desc, noteTimestamp = noteTimestamp)!!
            notesRepository.updateNote(note)
        }
    }
}