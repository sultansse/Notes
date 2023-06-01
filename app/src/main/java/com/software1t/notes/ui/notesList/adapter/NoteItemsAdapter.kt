package com.software1t.notes.ui.notesList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import com.software1t.notes.R

class NoteItemsAdapter(
    private val navController: NavController
) : ListAdapter<NoteItem, NoteItemViewHolder>(NoteItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteItemViewHolder(adapterLayout, navController)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun removeItem(position: Int) {
        val items = currentList.toMutableList()
        items.removeAt(position)
        submitList(items)
    }

    fun archiveItem(position: Int) {
        val items = currentList.toMutableList()
        val archivedItem = items.removeAt(position)
        // Perform archive action on the item
        // ...
        submitList(items)
    }
}