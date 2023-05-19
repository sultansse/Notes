package com.software1t.notes.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.software1t.notes.data.MockData
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase
import com.software1t.notes.ui.home.recyclerview.NoteItem

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {


    var isGrid = MutableLiveData(false)

    private val noteDao = NoteDatabase.getInstance(application).noteDao()
    private val query = MutableLiveData<String>()
    private val allNotes = noteDao.getAllNotes()
    val isDataEmpty = MutableLiveData(false)

    private var _notes = MediatorLiveData<List<Note>>()
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

    init {
//        deleteAllMockData()
//        insertMockData()
        updateFilteredNotes()
    }

    fun onSearchDataChange(query: String?) {
        this.query.value = query
    }

    private fun updateFilteredNotes() {
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
            isDataEmpty.value = filteredNotes.isEmpty()
            _notes.value = filteredNotes
        }
    }

    private fun insertMockData() {
        noteDao.insertAllNotes(MockData().mockNotes)
    }

    private fun deleteAllMockData() {
        noteDao.deleteAllNotes()
    }

    fun onLayoutManagerIconClick() {
        isGrid.value = !(isGrid.value ?: true)
    }
}
