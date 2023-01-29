package com.software1t.notes.ui.home.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.software1t.notes.R


class NotesAdapter :
    ListAdapter<NoteItem, NotesAdapter.ItemViewHolder>(
        RowItemDiffCallbackCity()
    ) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: MaterialTextView = view.findViewById(R.id.title_textView)
        val description: MaterialTextView = view.findViewById(R.id.desc_textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.description.text = item.description

        //bad because a lot of listener calls
        holder.itemView.setOnClickListener() {
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.action_containerFragment_to_noteFragment)
        }
    }

}

class RowItemDiffCallbackCity : DiffUtil.ItemCallback<NoteItem>() {

    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
        return oldItem == newItem
    }

}
