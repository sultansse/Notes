package com.software1t.notes.ui.home.recyclerview

import com.software1t.notes.data.Note

data class NoteItem(
    val id: Long = 0,
    val title: String,
    val description: String,
) {
    constructor(note: Note) : this(note.id, note.title, note.description)
}