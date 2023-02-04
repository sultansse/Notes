package com.software1t.notes.ui.edit_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase

class EditNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao()

    fun getNote(noteId: Long): LiveData<Note> {
        return noteDao.getNote(noteId)
    }

    fun insertNote(title: String, desc: String) {
        noteDao.insertNote(Note(title = title, description = desc))
    }

    fun updateNote(id: Long, title: String, desc: String) {
        noteDao.updateNote(Note(id = id, title = title, description = desc))
    }


}