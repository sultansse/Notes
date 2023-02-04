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
    private var _notes = MediatorLiveData<List<Note>>()
    private val allNotes = noteDao.getAllNotes()
    val notes: LiveData<List<NoteItem>>
        get() = _notes.map {
            it.map { note ->
                NoteItem(
                    note.id,
                    note.title,
                    note.description
                )
            }
        }
    private val query = MutableLiveData<String>()

    init {
        insertMockData()
        _notes.addSource(allNotes) { noteList ->
            _notes.value = noteList
        }
        _notes.addSource(query) { newQuery ->
            var filteredNotes = allNotes.value ?: return@addSource

            if (!newQuery.isNullOrBlank()) {
                val lowerCaseQuery = newQuery.lowercase()
                filteredNotes = filteredNotes.filter { note ->
                    note.title.lowercase().contains(lowerCaseQuery) || note.description.lowercase()
                        .contains(lowerCaseQuery)
                }
            }
            if (filteredNotes.isEmpty()) isDataEmpty = true
            _notes.value = filteredNotes
        }
    }

    fun onSearchDataChange(query: String?) {
        this.query.value = query
    }


    private fun insertMockData() {
        noteDao.insertAllNotes(MockData().mockNotes)
    }

}
