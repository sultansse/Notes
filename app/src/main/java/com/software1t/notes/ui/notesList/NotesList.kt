package com.software1t.notes.ui.notesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.databinding.FragmentNoteListBinding
import com.software1t.notes.ui.notesList.adapter.NoteItemsAdapter
import com.software1t.notes.ui.notesList.logic.LayoutManagerSwitch
import com.software1t.notes.ui.notesList.logic.NavigationDrawerHelper


class NotesList : Fragment() {
    private val viewModel: NotesListViewModel by viewModels()
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteItemsAdapter
    private lateinit var layoutManagerSwitch: LayoutManagerSwitch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        adapter = NoteItemsAdapter(navController = findNavController())

        setupRecyclerView()

        layoutManagerSwitch = LayoutManagerSwitch(binding, recyclerView, viewModel)

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }

        binding.fab.setOnClickListener {
            val action = NotesListDirections.actionNoteListFragmentToEditNoteFragment(noteId = -1)
            findNavController().navigate(action)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchQueryChanged(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearchQueryChanged(query)
                return true
            }
        })

        val navigationDrawerHelper = NavigationDrawerHelper(requireActivity() as AppCompatActivity)
        navigationDrawerHelper.setupNavigationDrawer()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        layoutManagerSwitch.onDestroy()
    }
}
