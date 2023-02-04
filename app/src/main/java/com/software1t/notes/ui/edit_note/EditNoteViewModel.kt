package com.software1t.notes.ui.edit_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase

class EditNoteViewModel(
    application: Application,
    private val noteId: Long,
) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao()
    val currentNote = noteDao.getNote(noteId)
    private var isNewNote = false

    init {
        if (noteId == -1L) isNewNote = true
    }

    fun onClickSubmit(title: String, desc: String) {
        if (isNewNote) {
            noteDao.insertNote(Note(title = title, description = desc))
        } else {
            noteDao.updateNote(Note(id = noteId, title = title, description = desc))
        }
    }
}