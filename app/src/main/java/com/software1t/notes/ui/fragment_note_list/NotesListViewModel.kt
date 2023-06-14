package com.software1t.notes.ui.fragment_note_list

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
import com.software1t.notes.utils.Constants.Companion.NOTE_LIST_PREFS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesListViewModel(
    application: Application,
    private val notesRepository: NotesRepository
) : AndroidViewModel(application) {

    private val sharedPreferences =
        application.getSharedPreferences(NOTE_LIST_PREFS, Context.MODE_PRIVATE)

    private val allNotes: LiveData<List<NotesEntity>> = notesRepository.getAllNotes()

    private val _isGrid = MutableLiveData<Boolean>().apply {
        value = sharedPreferences.getBoolean(LAYOUT_PREF_KEY, false)
    }
    val isGrid: LiveData<Boolean> get() = _isGrid

    private val _notes = MediatorLiveData<List<NoteItem>>().apply {
        addSource(allNotes) { notes ->
            val noteItems = notes.map { note ->
                // Convert NotesEntity to NoteItem
                NoteItem(note.id, note.title, note.content)
            }
            postValue(noteItems)
        }
    }
    val notes: LiveData<List<NoteItem>> = _notes

    init {
//        deleteAllMockData()
//        insertMockData()
    }

    fun onDeleteNote(noteId: Long) {
        viewModelScope.launch {
            notesRepository.deleteNoteById(noteId)
        }
    }

    fun onLayoutManagerIconClick() {
        _isGrid.value = _isGrid.value?.not() ?: false
        saveLayoutManagerState(_isGrid.value!!)
    }

    fun onSearchQueryChanged(query: String?) {
        val filteredNotes = allNotes.value?.filter { note ->
            note.title.contains(query.toString(), ignoreCase = true) || note.content.contains(query.toString(), ignoreCase = true)
        }?.map { note ->
            // Convert NotesEntity to NoteItem
            NoteItem(note.id, note.title, note.content)
        }
        filteredNotes?.let {
            _notes.postValue(it)
        }
    }

    private fun saveLayoutManagerState(value: Boolean) {
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
