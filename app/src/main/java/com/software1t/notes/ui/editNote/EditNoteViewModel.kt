package com.software1t.notes.ui.editNote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.software1t.notes.data.local.entities.NotesEntity
import com.software1t.notes.domain.repository.NotesRepository
import com.software1t.notes.domain.useсases.SaveButtonClick
import com.software1t.notes.ui.model.NoteItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteViewModel(
    application: Application,
    private val noteId: Long,
    private val notesRepository: NotesRepository
) : AndroidViewModel(application) {

    private var _notes = MutableLiveData<List<NoteItem>>()
    val notes: LiveData<List<NoteItem>> get() = _notes

    var currentNote: LiveData<NotesEntity> = notesRepository.getNote(noteId)

    fun onClickSave(title: String, desc: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val obj = SaveButtonClick(notesRepository) //todo inject as parameter
            obj.execute(title, desc, noteId)
        }
    }

    fun deleteNote() {
        CoroutineScope(Dispatchers.IO).launch {
            notesRepository.deleteNote(currentNote.value!!)
        }
    }

    fun copyNote() {
        CoroutineScope(Dispatchers.IO).launch {
            notesRepository.insertNote(
                NotesEntity(
                    title = "${currentNote.value?.title} COPY",
                    description = currentNote.value?.description.toString(),
                    noteTimestamp = currentNote.value?.noteTimestamp!!
                )
            )
        }

    }
}