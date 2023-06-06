package com.software1t.notes.ui.notesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.databinding.FragmentNoteListBinding
import com.software1t.notes.domain.useсases.LayoutManagerSwitch
import com.software1t.notes.domain.useсases.NavigationDrawerHelper
import com.software1t.notes.ui.adapter.NoteItemsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotesList : Fragment() {
    private val viewModel: NotesListViewModel by viewModel { parametersOf(requireActivity().application)}
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


        setupRecyclerView()
        setupRecyclerViewGestures()

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
        recyclerView = binding.recyclerView
        //todo : change adapter's parameters
        adapter = NoteItemsAdapter(navController = findNavController()/*viewModel.noteId , navController = findNavController(), notesRepository = viewModel.notesRepository*/)
        recyclerView.adapter = adapter
    }

    private fun setupRecyclerViewGestures() {
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // No implementation needed for swipe
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    // Perform delete action
                    Toast.makeText(requireContext(), "Deleted item ", Toast.LENGTH_SHORT).show()
                    adapter.removeItem(position)
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Perform archive action
                    Toast.makeText(requireContext(), "Archived item", Toast.LENGTH_SHORT).show()
                    adapter.archiveItem(position)
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        layoutManagerSwitch.onDestroy()
    }
}
