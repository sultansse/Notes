package com.software1t.notes.ui.notesList.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.software1t.notes.R
import com.software1t.notes.databinding.NoteItemBinding
import com.software1t.notes.ui.model.NoteItem


class NoteItemsAdapter : ListAdapter<NoteItem, NoteItemsAdapter.ItemViewHolder>(
    RowItemDiffCallbackCity()
) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NoteItemBinding.bind(view)

        val title: MaterialTextView = binding.titleTextView
        val description: MaterialTextView = binding.descTextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.description.text = item.description

        holder.itemView.setOnClickListener {
            val itemId = getItem(holder.adapterPosition).id
            val bundle = Bundle().apply {
                putLong("note_id", itemId)
            }
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.action_homeFragment_to_editNoteFragment, bundle)
        }
    }
}

class RowItemDiffCallbackCity : DiffUtil.ItemCallback<NoteItem>() {

    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem.id == newItem.id
    }
}

