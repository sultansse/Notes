package com.software1t.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import com.software1t.notes.databinding.NoteItemBinding
import com.software1t.notes.ui.model.NoteItem
import com.software1t.notes.ui.notesList.NotesListDirections

class NoteItemsAdapter(
    private val navController: NavController
) : ListAdapter<NoteItem, NoteItemViewHolder>(NoteItemDiffCallback()), NoteItemClickListener {

    override fun onNoteItemClick(item: NoteItem) {
        val action = NotesListDirections.actionNoteListFragmentToEditNoteFragment(item.id)
        navController.navigate(action)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteItemViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun removeItem(position: Int) {
        val items = currentList.toMutableList()
        items.removeAt(position)
        // Perform delete (trash) action on the item
        // ...
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