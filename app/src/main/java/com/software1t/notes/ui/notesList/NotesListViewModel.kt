package com.software1t.notes.ui.notesList

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.software1t.notes.data.local.MockData
import com.software1t.notes.data.local.NotesEntity
import com.software1t.notes.domain.repository.NotesRepository
import com.software1t.notes.ui.model.NoteItem
import com.software1t.notes.utils.Constants.Companion.LAYOUT_PREF_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesListViewModel(
    application: Application,
    private val notesRepository: NotesRepository
) : AndroidViewModel(application) {

    private val sharedPreferences =
        application.getSharedPreferences("NotesListPrefs", Context.MODE_PRIVATE)

    private val allNotes: LiveData<List<NotesEntity>> = notesRepository.getAllNotes()

    private val _isGrid = MutableLiveData<Boolean>().apply {
        value = sharedPreferences.getBoolean(LAYOUT_PREF_KEY, false)
    }
    val isGrid: LiveData<Boolean> get() = _isGrid

    private val _notes = MediatorLiveData<List<NoteItem>>()
    val notes: LiveData<List<NoteItem>> = _notes

    private val query = MutableLiveData<String>()
    private val isDataEmpty = MutableLiveData(false)

    init {
//        deleteAllMockData()
//        insertMockData()


        updateFilteredNotes()
    }

    fun onDeleteNote(noteId: Long) {
        viewModelScope.launch {
            notesRepository.deleteNoteById(noteId)
        }
    }

    fun onLayoutManagerIconClick() {
        _isGrid.value = _isGrid.value?.not() ?: false
        saveIsGrid(_isGrid.value!!)
    }

    fun onSearchQueryChanged(query: String?) {
        this.query.value = query
    }

    private fun updateFilteredNotes() {
        _notes.addSource(allNotes) { noteList ->
            filterAndSetNotes(noteList)
        }
        _notes.addSource(query) {
            val noteList = allNotes.value.orEmpty()
            filterAndSetNotes(noteList)
        }
    }

    private fun filterAndSetNotes(noteList: List<NotesEntity>) {
        val currentQuery = query.value?.lowercase()
        val filteredNotes = if (currentQuery.isNullOrBlank()) {
            noteList.map { note ->
                NoteItem(note.id, note.title, note.content)
            }
        } else {
            val lowerCaseQuery = currentQuery.lowercase()
            noteList.filter { note ->
                note.title.lowercase().contains(lowerCaseQuery) || note.content.lowercase()
                    .contains(lowerCaseQuery)
            }.map { note ->
                NoteItem(note.id, note.title, note.content)
            }
        }
        isDataEmpty.value = filteredNotes.isEmpty()
        _notes.value = filteredNotes
    }

    private fun saveIsGrid(value: Boolean) {
        viewModelScope.launch {
            sharedPreferences.edit().putBoolean(LAYOUT_PREF_KEY, value).apply()
        }
    }

    private fun insertMockData() {
        viewModelScope.launch(Dispatchers.IO) {
            val mockNotes = MockData.mockNotes
            notesRepository.insertAllNotes(mockNotes)
        }
    }

    private fun deleteAllMockData() {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteAllNotes()
        }
    }
}
