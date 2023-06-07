package com.software1t.notes.domain.useсases

import com.software1t.notes.data.local.entities.NotesEntity
import com.software1t.notes.domain.repository.NotesRepository

class DeleteNote(private val notesRepository: NotesRepository) {
    suspend fun execute(note: NotesEntity) {
        notesRepository.deleteNote(note)
    }
}