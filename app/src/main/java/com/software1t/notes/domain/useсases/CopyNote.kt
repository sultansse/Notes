package com.software1t.notes.domain.useсases

import com.software1t.notes.data.local.entities.NotesEntity
import com.software1t.notes.domain.repository.NotesRepository

class CopyNote(private val notesRepository: NotesRepository) {
    suspend fun execute(note: NotesEntity) {
        notesRepository.insertNote(
            NotesEntity(
                title = note.title,
                description = note.description,
                noteTimestamp = note.noteTimestamp
            )
        )
    }

}