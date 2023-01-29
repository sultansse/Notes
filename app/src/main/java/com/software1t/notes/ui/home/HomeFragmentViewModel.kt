package com.software1t.notes.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.software1t.notes.data.MockData
import com.software1t.notes.data.NoteDatabase
import com.software1t.notes.ui.home.recyclerview.NoteItem

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao()
    private fun insertMockData() {
//        for (note in MockData().mockNotes) noteDao.insertNote(note) //111
    }

    //    private var _notes: MutableLiveData<List<Note>> = noteDao.getAllNotes() as MutableLiveData<List<Note>> //111
    private var _notes: MutableLiveData<List<NoteItem>> = MutableLiveData() //222
    var isEmpty: Boolean = false

    init {
        _notes.value = MockData().allCities //222
//        insertMockData() //1111
    }

    //    val notes: LiveData<List<Note>> get() = _notes //11111
    val notes: LiveData<List<NoteItem>> get() = _notes //222

    fun onSearchDataChange(query: String?) {
        var temp: MutableList<NoteItem> = MockData().allCities.toMutableList()
        if (query.toString() != "") {
            temp = temp.filter { note ->
                note.toString().lowercase().contains(query.toString().lowercase())
            }.toMutableList()

        }
        if (temp.isEmpty()) {
            isEmpty = true
        }
        _notes.value = temp
    }


//    private fun insertNote(note: Note) {
//        noteDao.insertNote(note)
//    }
//
//    private fun updateNote(note: Note) {
//        noteDao.updateNote(note)
//    }
//
//    private fun deleteNote(note: Note) {
//        noteDao.deleteNote(note)
//    }
}