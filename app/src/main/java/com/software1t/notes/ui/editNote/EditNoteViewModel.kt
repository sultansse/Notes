package com.software1t.notes.ui.editNote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.software1t.notes.data.local.NoteDatabase
import com.software1t.notes.data.local.model.NoteLocalModel
import com.software1t.notes.ui.notesList.adapter.NoteItem
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
                noteDao.insertNote(NoteLocalModel(title = title, description = desc))
            } else {
                noteDao.updateNote(NoteLocalModel(id = noteId, title = title, description = desc))
            }
        }
    }

    fun deleteNote() {
        noteDao.deleteNote(currentNote.value!!)
    }

    fun copyNote() {
        noteDao.insertNote(
            NoteLocalModel(
                title = "${currentNote.value?.title} COPY",
                description = currentNote.value?.description.toString()
            )
        )
    }
}