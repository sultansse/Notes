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
import com.software1t.notes.utils.Constants.Companion.NEW_EMPTY_NOTE_ID
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotesList : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private var _recyclerView: RecyclerView? = null
    private val recyclerView get() = _recyclerView!!

    private var _adapter: NoteItemsAdapter? = null
    private val adapter get() = _adapter!!

    private lateinit var layoutManagerSwitch: LayoutManagerSwitch

    private val viewModel: NotesListViewModel by viewModel { parametersOf(requireActivity().application) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
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
            val action = NotesListDirections.actionNoteListFragmentToEditNoteFragment(NEW_EMPTY_NOTE_ID)
            findNavController().navigate(action)
        }

        binding.searchView.setOnQueryTextListener(this)

        val navigationDrawerHelper = NavigationDrawerHelper(requireActivity() as AppCompatActivity)
        navigationDrawerHelper.setupNavigationDrawer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
        _recyclerView = null
        layoutManagerSwitch.onDestroy()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.onSearchQueryChanged(newText)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.onSearchQueryChanged(query)
        return true
    }

    private fun setupRecyclerView() {
        _recyclerView = binding.recyclerView
        _adapter = NoteItemsAdapter(findNavController())
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
                val position = viewHolder.bindingAdapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    // Perform delete action
                    Toast.makeText(requireContext(), "Deleted item", Toast.LENGTH_SHORT).show()
                    val note = adapter.currentList[position]
                    viewModel.onDeleteNote(note.id)
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Perform archive action
                    Toast.makeText(requireContext(), "Archived item", Toast.LENGTH_SHORT).show()
                    val note = adapter.currentList[position]
                    viewModel.onDeleteNote(note.id)
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}