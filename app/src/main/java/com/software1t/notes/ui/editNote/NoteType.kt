package com.software1t.notes.ui.editNote

sealed class NoteType {

    object NewNote : NoteType()
    data class ExistingNote(val noteId: Long) : NoteType()
}
