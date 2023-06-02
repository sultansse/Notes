package com.software1t.notes.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.software1t.notes.ui.model.NoteItem

class NoteItemDiffCallback : DiffUtil.ItemCallback<NoteItem>() {

    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem == newItem
    }
}