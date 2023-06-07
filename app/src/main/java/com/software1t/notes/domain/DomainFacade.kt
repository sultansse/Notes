package com.software1t.notes.domain

import com.software1t.notes.domain.useсases.CopyNote
import com.software1t.notes.domain.useсases.SaveButtonClick

class DomainFacade(
    private val onSaveButtonClick: SaveButtonClick,
    private val onCopyNoteClick: CopyNote,
    private val onDeleteNoteClick: SaveButtonClick,

    ) {

    suspend fun executeSaveButtonClick(title: String, desc: String, noteId: Long) {
        onSaveButtonClick.execute(title, desc, noteId)
    }

//    suspend fun executeSaveButtonClick(note: NotesEntity) {
//        onSaveButtonClick.execute(note)
//    }

//    suspend fun executeCopyNoteClick(note: NotesEntity) {
//        onCopyNoteClick.execute(note)
//    }

//    suspend fun executeDeleteNoteClick(note: NotesEntity) {
//        onDeleteNoteClick.execute(note)
//    }
}
