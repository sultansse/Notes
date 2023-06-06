package com.software1t.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import com.software1t.notes.databinding.NoteItemBinding
import com.software1t.notes.ui.model.NoteItem

class NoteItemsAdapter(
//    private val noteId: Long,
    private val navController: NavController,
//    private val notesRepository: NotesRepository,

) : ListAdapter<NoteItem, NoteItemViewHolder>(NoteItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteItemViewHolder(binding, navController)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun removeItem(position: Int) {
        val items = currentList.toMutableList()
//        CoroutineScope(Dispatchers.IO).launch {
//            notesRepository.deleteNote(notesEntity = notesRepository.getNote(items[position].id).value!!)
//        }
        items.removeAt(position)
        submitList(items)
    }

    fun archiveItem(position: Int) {
        val items = currentList.toMutableList()
//        CoroutineScope(Dispatchers.IO).launch {
//            val myItemId = items[position].id
//            val currentNote = notesRepository.getNote(myItemId).value!!
//            notesRepository.deleteNote(notesEntity = currentNote)
//        }
        val archivedItem = items.removeAt(position)
        // Perform archive action on the item
        // ...
        submitList(items)
    }
}