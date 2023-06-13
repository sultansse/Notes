package com.software1t.notes.ui.editNote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.software1t.notes.data.local.NotesEntity
import com.software1t.notes.domain.repository.NotesRepository
import com.software1t.notes.utils.Constants.Companion.NEW_EMPTY_NOTE_ID
import kotlinx.coroutines.launch

class EditNoteViewModel(
    application: Application, private val noteId: Long, private val notesRepository: NotesRepository
) : AndroidViewModel(application) {

    private var _isNewNote: LiveData<Boolean>? = MutableLiveData((noteId == NEW_EMPTY_NOTE_ID))
    val isNewNote get() = _isNewNote!!

    private val isNewNoteValue = isNewNote.value!!

    val currentNote: LiveData<NotesEntity> = getCurrentNote(isNewNoteValue, noteId)


    fun onSaveNote(title: String, content: String, lastModifiedTime: Long) {
        viewModelScope.launch {
            val note = NotesEntity(
                id = if (isNewNoteValue) NEW_EMPTY_NOTE_ID else noteId,
                title = title,
                content = content,
                lastModifiedTime = lastModifiedTime
            )

            if (isNewNoteValue) {
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
                id = NEW_EMPTY_NOTE_ID,
                title = "${currentNoteValue.title} (Copy)",
                lastModifiedTime = System.currentTimeMillis()
            )
            notesRepository.insertNote(newNote)
        }
    }

    private fun getCurrentNote(isNewNoteValue: Boolean, noteId: Long): LiveData<NotesEntity> {

        val newNote = NotesEntity(
            id = NEW_EMPTY_NOTE_ID, title = "", content = "", lastModifiedTime = System.currentTimeMillis()
        )

        return if (isNewNoteValue) {
            MutableLiveData(newNote)
        } else {
            notesRepository.getNoteById(noteId)
        }
    }
}
