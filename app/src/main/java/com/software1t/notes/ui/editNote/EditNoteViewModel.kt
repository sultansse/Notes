package com.software1t.notes.ui.editNote

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.software1t.notes.data.local.entities.NotesEntity
import com.software1t.notes.data.local.model.NoteTimestamp
import com.software1t.notes.domain.repository.NotesRepository
import com.software1t.notes.domain.useсases.SaveButtonClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNoteViewModel(
    application: Application,
    private val noteId: Long,
    private val notesRepository: NotesRepository,
//    private val domainFacade: DomainFacade
) : AndroidViewModel(application) {

    var currentNote: LiveData<NotesEntity> = notesRepository.getNote(noteId)

    /*    val noteType: MutableLiveData<NoteType> = MutableLiveData()
        fun createNewNote() {
            noteType.value = NoteType.NewNote
        }
        fun loadExistingNote(noteId: Long) {
            noteType.value = NoteType.ExistingNote(noteId)
        }*/

    fun onClickSave(title: String, desc: String) {
        CoroutineScope(Dispatchers.IO).launch {
//            val  myNewNote = NotesEntity(
//                title = title,
//                description = desc,
//                noteTimestamp = NoteTimestamp(System.currentTimeMillis(), System.currentTimeMillis())
//            )
//            domainFacade.executeSaveButtonClick(currentNote.value?: myNewNote)
//            domainFacade.executeSaveButtonClick(title, desc, noteId)
            val obj = SaveButtonClick(notesRepository) //todo inject as parameter
            obj.execute(title, desc, noteId)
        }
    }

    fun onDeleteNote() {
        CoroutineScope(Dispatchers.IO).launch {
            if (currentNote.value == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "Unable to delete unsaved note", Toast.LENGTH_SHORT).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "Deleted successfully!", Toast.LENGTH_SHORT).show()
                }
                notesRepository.deleteNote(currentNote.value!!)
            }
        }
    }

    fun onCopyNote() {
        CoroutineScope(Dispatchers.IO).launch {
            notesRepository.insertNote(
                NotesEntity(
                    title = "${currentNote.value?.title} COPY",
                    description = currentNote.value?.description.toString(),
                    noteTimestamp = currentNote.value?.noteTimestamp ?: NoteTimestamp(
                        createdAt = System.currentTimeMillis(),
                        lastModifiedAt = System.currentTimeMillis()
                    )
                )
            )
        }

    }
}