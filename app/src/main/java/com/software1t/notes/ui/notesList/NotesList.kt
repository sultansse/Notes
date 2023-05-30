package com.software1t.notes.ui.notesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.software1t.notes.R
import com.software1t.notes.databinding.FragmentHomeBinding
import com.software1t.notes.ui.notesList.adapter.NoteItemsAdapter


class NotesList : Fragment() {

    private val viewModel: NotesListViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var _recyclerView: RecyclerView? = null
    private val recyclerView get() = _recyclerView!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _recyclerView = binding.recyclerView

        val adapter = NoteItemsAdapter()
        recyclerView.adapter = adapter

        binding.layoutManagerIconImageView.setOnClickListener {
            viewModel.onLayoutManagerIconClick()
        }

        viewModel.isGrid.observe(viewLifecycleOwner) { isGrid ->
            val layoutManager = if (isGrid) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
            recyclerView.layoutManager = layoutManager
        }

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchQueryChanged(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearchQueryChanged(query)
                return false
            }
        })
        // Setup navigation drawer
        val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = requireActivity().findViewById<NavigationView>(R.id.navView)

        binding.menuImageView.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.notifications -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.settings -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.questions -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.rate -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                // Add more navigation item cases as needed
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _recyclerView = null
    }
}
