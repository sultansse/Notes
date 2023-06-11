package com.software1t.notes.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.databinding.NoteItemBinding
import com.software1t.notes.ui.model.NoteItem


class NoteItemViewHolder(
    private val binding: NoteItemBinding, private val listener: NoteItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val item = view.tag as? NoteItem
        listener.onNoteItemClick(item ?: return)
    }

    fun bind(item: NoteItem) {
        val hasTitle = item.title.isNotEmpty()
        val hasContent = item.description.isNotEmpty()

        if (hasTitle && hasContent) {
            binding.titleTv.visibility = View.VISIBLE
            binding.contentTv.visibility = View.VISIBLE
        } else if (hasTitle) {
            binding.titleTv.visibility = View.VISIBLE
            binding.contentTv.visibility = View.GONE
        } else if (hasContent) {
            binding.titleTv.visibility = View.GONE
            binding.contentTv.visibility = View.VISIBLE
        } else {
            binding.titleTv.visibility = View.VISIBLE
            binding.contentTv.visibility = View.GONE
        }

        binding.titleTv.text = item.title
        binding.contentTv.text = item.description
        itemView.tag = item
    }
}