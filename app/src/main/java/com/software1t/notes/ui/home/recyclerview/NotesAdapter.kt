package com.software1t.notes.ui.home.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.R


class NotesAdapter :
    ListAdapter<Note, NotesAdapter.ItemViewHolder>(
        RowItemDiffCallbackCity()
    ) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_textView)
        val desc: TextView = view.findViewById(R.id.desc_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.desc.text = item.desc

        holder.itemView.findViewById<CardView>(R.id.item_cardView).setOnClickListener() {
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.action_containerFragment_to_noteFragment)
        }
    }

}

class RowItemDiffCallbackCity : DiffUtil.ItemCallback<Note>() {

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}
