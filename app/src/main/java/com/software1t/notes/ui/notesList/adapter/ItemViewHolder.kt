package com.software1t.notes.ui.notesList.adapter

import android.view.View
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.software1t.notes.databinding.NoteItemBinding
import com.software1t.notes.ui.notesList.NotesListDirections


class ItemViewHolder(
    view: View,
    private val navController: NavController
) : RecyclerView.ViewHolder(view) {

    private val binding = NoteItemBinding.bind(view)
    private val title: MaterialTextView = binding.titleTextView
    private val description: MaterialTextView = binding.descTextView

    fun bind(item: NoteItem) {
        title.text = item.title
        description.text = item.description

        itemView.setOnClickListener {
            val itemId = item.id
            val action = NotesListDirections.actionNoteListFragmentToEditNoteFragment(noteId = itemId)
            navController.navigate(action)
        }
    }
}