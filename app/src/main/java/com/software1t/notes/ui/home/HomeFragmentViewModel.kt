package com.software1t.notes.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.data.MockData
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase
import com.software1t.notes.ui.home.adapterPattern.NoteItemBlock
import com.software1t.notes.ui.home.adapterPattern.NoteItemImpl
import com.software1t.notes.ui.home.recyclerview.NoteItem

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {


    var isLinear = MutableLiveData(true)
    private var _layoutManager = MutableLiveData<RecyclerView.LayoutManager>()

    private val noteDao = NoteDatabase.getInstance(application).noteDao()
    private val query = MutableLiveData<String>()
    private val allNotes = noteDao.getAllNotes()
    val isDataEmpty = MutableLiveData(false)

    private val noteAdapter: NoteItemBlock = NoteItemImpl()
    private var _notes = MediatorLiveData<List<Note>>()
    val notes: LiveData<List<NoteItem>>
        get() = _notes.map {
            it.map { note ->
                noteAdapter.adapt(note)
            }
        }


//    fun onLayoutManagerChange() {
//        if (isLinear.value == true) {
//            _layoutManager.value = GridLayoutManager(getApplication(), 2)
//            _layoutManagerIcon.value = R.drawable.ic_outline_linear_view_24
//            isLinear.value = false
//        } else {
//            _layoutManager.value = LinearLayoutManager(getApplication())
//            _layoutManagerIcon.value = R.drawable.ic_baseline_grid_view_24
//            isLinear.value = true
//        }
//    }

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
}
