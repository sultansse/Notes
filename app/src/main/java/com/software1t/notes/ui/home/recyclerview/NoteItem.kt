package com.software1t.notes.ui.home.recyclerview

import com.software1t.notes.data.Note

data class NoteItem(
    val title: String,
    val description: String,
) {
    constructor(note: Note) : this(note.title, note.description)
}