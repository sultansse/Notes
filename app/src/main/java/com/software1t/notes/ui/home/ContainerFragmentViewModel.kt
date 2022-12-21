package com.software1t.notes.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software1t.notes.data.MockData
import com.software1t.notes.ui.home.recyclerview.Note

class ContainerFragmentViewModel : ViewModel() {

    //    private val mockData = MockData.getInstance()
    private var _notes: MutableLiveData<List<Note>> = MutableLiveData()
    val notes: LiveData<List<Note>> get() = _notes

    init {
        _notes.value = MockData().allCities
    }

    fun onSearchDataChange(query: String?) {
        var temp: MutableList<Note> = MockData().allCities.toMutableList()
        if (query.toString() != "") {
            temp = temp.filter { note ->
                note.toString().lowercase().contains(query.toString().lowercase())
            }.toMutableList()

        }
        _notes.value = temp
    }
}