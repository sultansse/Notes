package com.software1t.notes.ui.model

data class NoteItem(
    val id: Long = 0,
    val title: String,
    val description: String,
//    val noteItemAppearance: NoteItemAppearance //todo add appearance for notes according to status of note
)