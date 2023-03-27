package com.software1t.notes.ui.home.adapterPattern

import com.software1t.notes.data.Note
import com.software1t.notes.ui.home.recyclerview.NoteItem

interface NoteItemBlock {
    fun adapt(note: Note): NoteItem
}