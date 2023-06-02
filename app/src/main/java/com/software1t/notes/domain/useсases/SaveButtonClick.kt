package com.software1t.notes.domain.useсases

import androidx.lifecycle.LiveData
import com.software1t.notes.data.local.entities.NotesEntity
import com.software1t.notes.data.local.model.NoteTimestamp
import com.software1t.notes.domain.repository.NotesRepository
import java.text.SimpleDateFormat

import java.util.Locale

//todo timestamp is shown as long, fix it
class SaveButtonClick(private val notesRepository: NotesRepository) { // Inject NotesRepository
    suspend fun execute(title: String, desc: String, currentNote: LiveData<NotesEntity>, isNewNote: Boolean) {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("HH:mm:ss - dd\\MM\\yyyy", Locale.getDefault())
        val formattedTimestamp = dateFormat.format(currentTime)

        val noteTimestamp = NoteTimestamp(
            createdAt = currentNote.value?.noteTimestamp?.createdAt ?: currentTime,
            lastModifiedAt = (dateFormat.parse(formattedTimestamp)?.time ?: currentTime)
        )

        val note = if (isNewNote) {
            NotesEntity(
                title = title,
                description = desc,
                noteTimestamp = noteTimestamp
            )
        } else {
            currentNote.value?.copy(
                title = title,
                description = desc,
                noteTimestamp = noteTimestamp
            )
        }

        note.let {
            if (isNewNote) {
                notesRepository.insertNote(it!!) // Use the repository to insert note
            } else {
                notesRepository.updateNote(it!!) // Use the repository to update note
            }
        }
    }
}