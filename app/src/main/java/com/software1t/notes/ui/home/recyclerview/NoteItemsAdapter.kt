package com.software1t.notes.ui.home.recyclerview

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


class NoteItemsAdapter : ListAdapter<NoteItem, NoteItemsAdapter.ItemViewHolder>(
    RowItemDiffCallbackCity()
) {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // idk how to use backing, because onViewRecycled() is only RecyclerviewAdapter method.
        // this will have memory leak :(
        private val binding = NoteItemBinding.bind(view)

        val title: MaterialTextView = binding.titleTextView
        val description: MaterialTextView = binding.descTextView

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
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("note_id", getItem(position).id)
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
