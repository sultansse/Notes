package com.software1t.notes.ui.home.adapterPattern

import com.software1t.notes.data.Note
import com.software1t.notes.ui.home.recyclerview.NoteItem

class NoteItemImpl : NoteItemBlock {
    override fun adapt(note: Note): NoteItem {
        return NoteItem(
            note.id,
            note.title,
            note.description,/*note.lastModified*/
        )
    }
}