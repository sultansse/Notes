package com.software1t.notes.ui.editNote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.software1t.notes.data.local.NotesEntity
import com.software1t.notes.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNoteViewModel(
    application: Application, noteId: Long, private val notesRepository: NotesRepository
) : AndroidViewModel(application) {

    private val _currentNote: MutableLiveData<NotesEntity> = MutableLiveData()
    val currentNote: LiveData<NotesEntity> get() = _currentNote

    private var isNewNote = (noteId == -1L)

    init {
        viewModelScope.launch {
            if (isNewNote) {
                val newNotePattern =
                    NotesEntity(
                        title = "",
                        content = "",
                        lastModifiedTime = System.currentTimeMillis()
                    )
                _currentNote.value = newNotePattern
            } else {
                withContext(Dispatchers.IO) {
                    val note = notesRepository.getNoteById(noteId)
                    _currentNote.postValue(note)
                }
            }
        }
    }

    fun onSaveNote() {
        viewModelScope.launch {
            val currentNoteValue = currentNote.value ?: return@launch
            withContext(Dispatchers.IO) {
                if (isNewNote) {
                    notesRepository.insertNote(currentNoteValue)
                } else {
                    notesRepository.updateNote(currentNoteValue)
                }
            }
        }
    }

    fun onDeleteNote() {
        viewModelScope.launch {
            val currentNoteValue = currentNote.value ?: return@launch
            withContext(Dispatchers.IO) { notesRepository.deleteNote(currentNoteValue) }
        }
    }

    fun onCopyNote() {
        viewModelScope.launch {
            val currentNoteValue = currentNote.value ?: return@launch
            val newNote = currentNoteValue.copy(
                title = "${currentNoteValue.title} COPY",
                lastModifiedTime = System.currentTimeMillis()
            )
            withContext(Dispatchers.IO) { notesRepository.insertNote(newNote) }
        }
    }
}