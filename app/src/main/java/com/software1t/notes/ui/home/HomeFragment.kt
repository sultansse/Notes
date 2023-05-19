package com.software1t.notes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.databinding.FragmentHomeBinding
import com.software1t.notes.ui.home.recyclerview.NoteItemsAdapter


class HomeFragment : Fragment() {

    private val viewModel: HomeFragmentViewModel by viewModels()
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
            recyclerView.layoutManager = if (isGrid) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
        }

        viewModel.notes.observe(viewLifecycleOwner) { noteItems ->
            adapter.submitList(noteItems)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _recyclerView = null
    }
}
