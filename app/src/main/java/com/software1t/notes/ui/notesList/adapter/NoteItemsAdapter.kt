package com.software1t.notes.ui.notesList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import com.software1t.notes.R

class NoteItemsAdapter(
    private val navController: NavController
) : ListAdapter<NoteItem, ItemViewHolder>(RowItemDiffCallbackCity()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ItemViewHolder(adapterLayout, navController)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}