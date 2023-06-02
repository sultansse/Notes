package com.software1t.notes.domain.useсases

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.R
import com.software1t.notes.databinding.FragmentNoteListBinding
import com.software1t.notes.ui.notesList.NotesListViewModel

class LayoutManagerSwitch(
    private val binding: FragmentNoteListBinding,
    private val recyclerView: RecyclerView,
    private val viewModel: NotesListViewModel
) {
    private val isGridObserver: Observer<Boolean> = Observer { isGrid ->
        val layoutManager: RecyclerView.LayoutManager = if (isGrid) {
            GridLayoutManager(binding.root.context, 2)
        } else {
            LinearLayoutManager(binding.root.context)
        }

        binding.layoutManagerIconImageView.setImageResource(
            if (isGrid) R.drawable.ic_outline_linear_view_24
            else R.drawable.ic_baseline_grid_view_24
        )

        recyclerView.layoutManager = layoutManager
    }

    init {
        binding.layoutManagerIconImageView.setOnClickListener {
            viewModel.onLayoutManagerIconClick()
        }

        viewModel.isGrid.observeForever(isGridObserver)
    }

    fun onDestroy() {
        viewModel.isGrid.removeObserver(isGridObserver)
    }
}
