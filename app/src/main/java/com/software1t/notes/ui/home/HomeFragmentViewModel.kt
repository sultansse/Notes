package com.software1t.notes.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.software1t.notes.data.MockData
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase
import com.software1t.notes.ui.home.recyclerview.NoteItem

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var isDataEmpty: Boolean = false
    private val noteDao = NoteDatabase.getInstance(application).noteDao()
    private var _notes = MutableLiveData<List<NoteItem>>()
    val notes: LiveData<List<NoteItem>> get() = _notes

    init {
        insertMockData()
    }

    fun observeNotes(lifecycleOwner: LifecycleOwner) {
        noteDao.getAllNotes().observe(lifecycleOwner, Observer { notes ->
            _notes.value = mapNotesToNoteItems(notes)
        })
    }

    fun onSearchDataChange(query: String?) {
        noteDao.getAllNotes().observeForever { notes ->
            var filteredNotes = notes

            if (!query.isNullOrBlank()) {
                val lowerCaseQuery = query.lowercase()
                filteredNotes = filteredNotes?.filter { note ->
                    note.title.lowercase().contains(lowerCaseQuery) || note.description.lowercase()
                        .contains(lowerCaseQuery)
                }
            }
            if (filteredNotes.isEmpty()) isDataEmpty = true
            _notes.value = mapNotesToNoteItems(filteredNotes)
        }
    }

    private fun mapNotesToNoteItems(notes: List<Note>): List<NoteItem> {
        return notes.map { note -> NoteItem(note.id, note.title, note.description) }
    }

    private fun insertMockData() {
        noteDao.deleteAllNotes()
        noteDao.insertAllNotes(MockData().mockNotes)
    }

}
