package com.software1t.notes.ui.edit_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase
import com.software1t.notes.ui.model.NoteItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteViewModel(
    application: Application,
    private val noteId: Long,
) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao()
    private var _notes = MutableLiveData<List<NoteItem>>()
    val notes: LiveData<List<NoteItem>> get() = _notes

    var currentNote = noteDao.getNote(noteId)
    private var isNewNote = false

    init {
        if (noteId == -1L) isNewNote = true
    }

    fun onClickSave(title: String, desc: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (isNewNote) {
                noteDao.insertNote(Note(title = title, description = desc))
            } else {
                noteDao.updateNote(Note(id = noteId, title = title, description = desc))
            }
        }
    }

    fun deleteNote() {
        noteDao.deleteNote(currentNote.value!!)
    }

    fun copyNote() {
        noteDao.insertNote(
            Note(
                title = "${currentNote.value?.title} COPY",
                description = currentNote.value?.description.toString()
            )
        )
    }
}