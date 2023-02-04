package com.software1t.notes.ui.edit_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.software1t.notes.data.Note
import com.software1t.notes.data.NoteDatabase

class EditNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getInstance(application).noteDao()

    fun getNote(noteId: Long): LiveData<Note> {
        return noteDao.getNote(noteId)
    }

    fun submitToDatabase(title: String, desc: String) {
        noteDao.insertNote(Note(title = title, description = desc))
    }

    /*   private var _title: MutableLiveData<String> = MutableLiveData<String>()
       val title: LiveData<String> get() = _title

       private var _desc: MutableLiveData<String> = MutableLiveData<String>()
       val desc: LiveData<String> get() = _desc

       fun observeNotes(lifecycleOwner: LifecycleOwner, id: Long) {
           noteDao.getAllNotes().observe(lifecycleOwner, Observer {
               Log.d(
                   "TAG",
                   "BEFORE EditNoteViewModel:noteDao.getNote(id).value?.title = ${noteDao.getNote(id).value?.title} "
               )
               _title.value = noteDao.getNote(id).value?.title
               _desc.value = noteDao.getNote(id).value?.description
               Log.d(
                   "TAG",
                   "AFTER EditNoteViewModel:noteDao.getNote(id).value?.title = ${_title.value} "
               )

           })
       }


       fun getNote(id: Long) {
           Log.d(
               "TAG",
               "BEFORE EditNoteViewModel:noteDao.getNote(id).value?.title = ${noteDao.getNote(id).value?.title} "
           )
           _title.value = noteDao.getNote(id).value?.title
           _desc.value = noteDao.getNote(id).value?.description
           Log.d("TAG", "AFTER EditNoteViewModel:noteDao.getNote(id).value?.title = ${_title.value} ")

       }*/


}