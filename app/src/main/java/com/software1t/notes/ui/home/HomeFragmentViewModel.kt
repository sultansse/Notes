package com.software1t.notes.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase
import com.software1t.notes.ui.home.recyclerview.NoteItem

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao()
    private val allNotes = noteDao.getAllNotes()

    var isGrid = MutableLiveData(false)
    private val query = MutableLiveData<String>()
    val isDataEmpty = MutableLiveData(false)

    private val _notes = MediatorLiveData<List<NoteItem>>()
    val notes: LiveData<List<NoteItem>> = _notes

    init {
        updateFilteredNotes()
    }

    fun onSearchQueryChanged(query: String?) {
        this.query.value = query
    }

    private fun updateFilteredNotes() {
        _notes.addSource(allNotes) { noteList ->
            val filteredNotes = filterNotes(noteList)
            isDataEmpty.value = filteredNotes.isEmpty()
            _notes.value = filteredNotes
        }
        _notes.addSource(query) { newQuery ->
            val noteList = allNotes.value ?: emptyList()
            val filteredNotes = filterNotes(noteList)
            isDataEmpty.value = filteredNotes.isEmpty()
            _notes.value = filteredNotes
        }
    }

    private fun filterNotes(noteList: List<Note>): List<NoteItem> {
        val currentQuery = query.value?.lowercase()
        return if (currentQuery.isNullOrBlank()) {
            noteList.map { note ->
                NoteItem(note.id, note.title, note.description)
            }
        } else {
            val lowerCaseQuery = currentQuery.lowercase()
            noteList.filter { note ->
                note.title.lowercase().contains(lowerCaseQuery) || note.description.lowercase()
                    .contains(lowerCaseQuery)
            }.map { note ->
                NoteItem(note.id, note.title, note.description)
            }
        }
    }

    fun onLayoutManagerIconClick() {
        isGrid.value = !(isGrid.value ?: true)
    }
}
