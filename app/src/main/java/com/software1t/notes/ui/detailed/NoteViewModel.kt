package com.software1t.notes.ui.detailed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel : ViewModel() {

    private var _title: MutableLiveData<String> = MutableLiveData<String>().apply { "" }
    val title: LiveData<String> get() = _title

    private var _desc: MutableLiveData<String> = MutableLiveData<String>().apply { "" }
    val desc: LiveData<String> get() = _desc

}