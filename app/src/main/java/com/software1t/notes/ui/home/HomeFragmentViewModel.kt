package com.software1t.notes.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.software1t.notes.data.MockData
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase
import com.software1t.notes.ui.home.recyclerview.NoteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var isEmpty: Boolean = false
    private val noteDao = NoteDatabase.getInstance(application).noteDao()
    private var _notes: MutableLiveData<List<NoteItem>>? = MutableLiveData()
    val notes: LiveData<List<NoteItem>> get() = _notes!!

    init {
        _notes = MutableLiveData()
        insertMockData()
        noteDao.getAllNotes().observeForever { notes ->
            if (notes != null)
                _notes?.value = mapNotesToNoteItems(notes)
        }
    }


    private fun mapNotesToNoteItems(notes: List<Note>): List<NoteItem> {
        return notes.map { note -> NoteItem(note.id, note.title, note.description) }
    }

    private fun insertMockData() {
        viewModelScope.launch(Dispatchers.IO) {

            noteDao.deleteAllNotes()
            for (note in MockData().mockNotes) insertNote(note)
        }
    }

    fun onSearchDataChange(query: String?) {

        var temp = noteDao.getAllNotes().value
        temp = temp?.filter { note ->
            note.toString().lowercase().contains(query.toString().lowercase())
        }?.toMutableList()
        if (temp?.isEmpty()!!) {
            isEmpty = true
        }
        _notes?.value = mapNotesToNoteItems(temp)
    }

    private suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.copy(id = 0))
    }
}