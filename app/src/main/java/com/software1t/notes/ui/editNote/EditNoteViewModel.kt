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
    private val notesRepository: NotesRepository // Inject NotesRepository
) : AndroidViewModel(application) {

    //   private val noteDao = NotesDatabase.getDatabase(application).noteDao()

    private var _notes = MutableLiveData<List<NoteItem>>()
    val notes: LiveData<List<NoteItem>> get() = _notes
    //todo timestamp is shown as long, fix it
    var currentNote: LiveData<NotesEntity> = notesRepository.getNote(noteId) // Retrieve note using the repository
    private var isNewNote = false

    init {
        if (noteId == -1L) isNewNote = true
    }


    fun onClickSave(title: String, desc: String) {
        CoroutineScope(Dispatchers.IO).launch {
         /*   val currentTime = System.currentTimeMillis()
            val noteTimestamp = NoteTimestamp(
                createdAt = currentNote.value?.noteTimestamp?.createdAt ?: currentTime,
                lastModifiedAt = currentTime
            )
            val note = if (isNewNote) {
                NotesEntity(
                    title = title,
                    description = desc,
                    noteTimestamp = noteTimestamp
                )
            } else {
                currentNote.value?.copy(
                    title = title,
                    description = desc,
                    noteTimestamp = noteTimestamp
                )
            }
            note.let {
                if (isNewNote) {
                    noteDao.insertNote(it)
                } else {
                    noteDao.updateNote(it)
                }
            }*/
            val obj = SaveButtonClick(notesRepository) //todo inject as parameter
            obj.execute(title, desc, currentNote, isNewNote)
        }
    }

    fun deleteNote() {
//        noteDao.deleteNote(currentNote.value!!)
    }

    fun copyNote() {
//        noteDao.insertNote(
//            NotesEntity(
//                title = "${currentNote.value?.title} COPY",
//                description = currentNote.value?.description.toString()
//            )
//        )
    }
}