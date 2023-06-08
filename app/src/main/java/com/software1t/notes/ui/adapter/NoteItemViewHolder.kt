package com.software1t.notes.ui.adapter

import android.view.View
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.databinding.NoteItemBinding
import com.software1t.notes.ui.model.NoteItem
import com.software1t.notes.ui.notesList.NotesListDirections


class NoteItemViewHolder(
    private val binding: NoteItemBinding,
    private val navController: NavController
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(item: NoteItem) {
        binding.titleTextView.text = item.title
        binding.descTextView.text = item.description
        binding.root.tag = item
    }

    override fun onClick(view: View) {
        val item = view.tag as? NoteItem
        if (item != null) {
            val itemId = item.id
            val action = NotesListDirections.actionNoteListFragmentToEditNoteFragment(noteId = itemId.toLong())
            navController.navigate(action)
        }
    }
}