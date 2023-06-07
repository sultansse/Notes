package com.software1t.notes.domain.useсases

import com.software1t.notes.data.local.entities.NotesEntity
import com.software1t.notes.data.local.model.NoteTimestamp
import com.software1t.notes.domain.repository.NotesRepository

class SaveButtonClick(private val notesRepository: NotesRepository) {
    suspend fun execute(title: String, desc: String, noteId: Long) {
        val currentNote = notesRepository.getNote(noteId)

//        class SaveButtonClick(private val notesRepository: NotesRepository) {
//            suspend fun execute(note: NotesEntity) {

        val currentTime = System.currentTimeMillis()
        val noteTimestamp = NoteTimestamp(
            createdAt = currentNote.value?.noteTimestamp?.createdAt ?: currentTime,
            lastModifiedAt = currentNote.value?.noteTimestamp?.lastModifiedAt ?: currentTime
        )

        val note = NotesEntity(
            id = noteId, title = title, description = desc, noteTimestamp = noteTimestamp
        )
        if (noteId == -1L) {
//            if (note.id == -1L) {
            notesRepository.insertNote(note)
        } else {
            notesRepository.updateNote(note)
        }
    }
}
