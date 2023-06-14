package com.software1t.notes.ui.fragment_note_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.R
import com.software1t.notes.databinding.FragmentNoteListBinding
import com.software1t.notes.domain.useсases.NavigationDrawerHelper
import com.software1t.notes.ui.MainActivity
import com.software1t.notes.ui.adapter.NoteItemsAdapter
import com.software1t.notes.utils.Constants.Companion.NEW_EMPTY_NOTE_ID
import com.software1t.notes.utils.Constants.Companion.SWIPEABILITY_PREF_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotesList : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private var _recyclerView: RecyclerView? = null
    private val recyclerView get() = _recyclerView!!

    private var _adapter: NoteItemsAdapter? = null
    private val adapter get() = _adapter!!

    private val viewModel: NotesListViewModel by viewModel { parametersOf(requireActivity().application) }

    // In NoteListFragment
    private lateinit var swipeConfiguration: SwipeConfiguration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        val sharedPreferences =
            requireActivity().getSharedPreferences(SWIPEABILITY_PREF_KEY, Context.MODE_PRIVATE)

        val swipable = sharedPreferences.getBoolean("GESTURES", true)
        setupRecyclerViewGestures(swipable)

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }

        viewModel.isGrid.observe(viewLifecycleOwner) {

            val isGridValue = it!!

            if (isGridValue) {
                binding.layoutManagerIconBtn.setImageResource(R.drawable.ic_layout_linearview)
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            } else {
                binding.layoutManagerIconBtn.setImageResource(R.drawable.ic_layout_gridview)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        val navigationDrawerHelper = NavigationDrawerHelper(requireActivity() as AppCompatActivity)
        navigationDrawerHelper.setupNavigationDrawer()

        binding.searchView.setOnQueryTextListener(this)

        binding.layoutManagerIconBtn.setOnClickListener {
            viewModel.onLayoutManagerIconClick()
        }

        binding.fab.setOnClickListener {
            val action =
                NotesListDirections.actionNoteListFragmentToEditNoteFragment(NEW_EMPTY_NOTE_ID)
            findNavController().navigate(action)
        }

        swipeConfiguration = (requireActivity() as MainActivity).swipeConfiguration

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
        _recyclerView = null
    }

    override fun onQueryTextChange(query: String?): Boolean {
        viewModel.onSearchQueryChanged(query)
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

    private fun setupRecyclerViewGestures(swipable: Boolean) {
        val swipeFlags = if (swipable) (ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) else 0

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, swipeFlags) {
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

                val swipeAction = when (direction) {
                    ItemTouchHelper.LEFT -> swipeConfiguration.swipeToLeftAction
                    ItemTouchHelper.RIGHT -> swipeConfiguration.swipeToRightAction
                    else -> null
                }

                swipeAction?.let { action ->
                    when (action) {
                        is SwipeAction.Archive -> {
                            // Perform archive action
                            Toast.makeText(requireContext(), "Archived item", Toast.LENGTH_SHORT).show()
                            val note = adapter.currentList[position]
                            viewModel.onDeleteNote(note.id) // todo archive
                        }
                        is SwipeAction.Delete -> {
                            // Perform delete action
                            Toast.makeText(requireContext(), "Deleted item", Toast.LENGTH_SHORT).show()
                            val note = adapter.currentList[position]
                            viewModel.onDeleteNote(note.id)
                        }
                        is SwipeAction.Custom -> {
                            // Perform custom action
                            Toast.makeText(requireContext(), "Custom action", Toast.LENGTH_SHORT).show()
                            // Implement your custom action logic here
                        }
                    }
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}