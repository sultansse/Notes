package com.software1t.notes.ui.editNote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.software1t.notes.data.local.NotesEntity
import com.software1t.notes.domain.repository.NotesRepository
import kotlinx.coroutines.launch

class EditNoteViewModel(
    application: Application,
    private val noteId: Long,
    private val notesRepository: NotesRepository
) : AndroidViewModel(application) {

    private val newNote = NotesEntity(
        id = 0,
        title = "",
        content = "",
        lastModifiedTime = System.currentTimeMillis()
    )

    val currentNote: LiveData<NotesEntity> = if (noteId == -1L) {
        MutableLiveData(newNote)
    } else {
        notesRepository.getNoteById(noteId)
    }


    private val isNewNote = (noteId == -1L)

    fun onSaveNote(title: String, content: String, lastModifiedTime: Long) {
        viewModelScope.launch {
            val note = NotesEntity(
                id = if (isNewNote) 0 else noteId,
                title = title,
                content = content,
                lastModifiedTime = lastModifiedTime
            )

            if (isNewNote) {
                notesRepository.insertNote(note)
            } else {
                notesRepository.updateNote(note)
            }
        }
    }

    fun onDeleteNote() {
        viewModelScope.launch {
            notesRepository.deleteNoteById(noteId)
        }
    }

    fun onCopyNote() {
        viewModelScope.launch {
            val currentNoteValue = currentNote.value!!

            val newNote = currentNoteValue.copy(
                id = 0,
                title = "${currentNoteValue.title} (Copy)",
                lastModifiedTime = System.currentTimeMillis()
            )
            notesRepository.insertNote(newNote)
        }
    }
}
