package com.software1t.notes.ui.notesList

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.software1t.notes.data.local.MockData
import com.software1t.notes.data.local.NoteDatabase
import com.software1t.notes.data.local.model.NoteLocalModel
import com.software1t.notes.ui.notesList.adapter.NoteItem

class NotesListViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao()
    private val allNotes = noteDao.getAllNotes()

    private val sharedPreferences =
        application.getSharedPreferences("HomeFragmentPrefs", Context.MODE_PRIVATE)
    private val layoutManagerKey = "layoutManager"
    private var savedLayoutManager: String? = null

    private val _isGrid = MutableLiveData(false)
    val isGrid: LiveData<Boolean> = _isGrid

    private val query = MutableLiveData<String>()
    val isDataEmpty = MutableLiveData(false)

    private val _notes = MediatorLiveData<List<NoteItem>>()
    val notes: LiveData<List<NoteItem>> = _notes

    init {
//        deleteAllMockData()
//        insertMockData()

        restoreLayoutManagerState()
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

    private fun filterNotes(noteLocalModelList: List<NoteLocalModel>): List<NoteItem> {
        val currentQuery = query.value?.lowercase()
        return if (currentQuery.isNullOrBlank()) {
            noteLocalModelList.map { note ->
                NoteItem(note.id, note.title, note.description)
            }
        } else {
            val lowerCaseQuery = currentQuery.lowercase()
            noteLocalModelList.filter { note ->
                note.title.lowercase().contains(lowerCaseQuery) || note.description.lowercase()
                    .contains(lowerCaseQuery)
            }.map { note ->
                NoteItem(note.id, note.title, note.description)
            }
        }
    }

    fun onLayoutManagerIconClick() {
        _isGrid.value = !_isGrid.value!!

        // Save the layout manager state
        val layoutManagerValue = if (_isGrid.value == true) {
            "grid"
        } else {
            "linear"
        }
        sharedPreferences.edit().putString(layoutManagerKey, layoutManagerValue).apply()
    }

    private fun restoreLayoutManagerState() {
        savedLayoutManager = sharedPreferences.getString(layoutManagerKey, null)
        savedLayoutManager?.let { layoutManagerValue ->
            _isGrid.value = layoutManagerValue == "grid"
        }
    }

    private fun insertMockData() {
        noteDao.insertAllNotes(MockData().mockNoteLocalModels)
    }

    private fun deleteAllMockData() {
        noteDao.deleteAllNotes()
    }
}
