package com.software1t.notes.ui.edit_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase

class EditNoteViewModel(application: Application) : AndroidViewModel(application) {

    private var _title: MutableLiveData<String> = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private var _desc: MutableLiveData<String> = MutableLiveData<String>()
    val desc: LiveData<String> get() = _desc

    private val noteDao = NoteDatabase.getInstance(application).noteDao()


    fun submitToDatabase(title: String, desc: String) {
        noteDao.insertNote(Note(title = title, description = desc))
    }

}