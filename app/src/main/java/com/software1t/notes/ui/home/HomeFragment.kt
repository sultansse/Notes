package com.software1t.notes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.software1t.notes.R
import com.software1t.notes.databinding.FragmentHomeBinding
import com.software1t.notes.ui.home.recyclerview.NoteItemsAdapter


class HomeFragment : Fragment() {

    private var isLinear = true
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
        recyclerView.layoutManager = LinearLayoutManager(context)


        binding.optionsImageView.setOnClickListener {
            if (isLinear) {
                recyclerView.layoutManager = GridLayoutManager(context, 2)
                binding.optionsImageView.setImageResource(R.drawable.ic_outline_linear_view_24)
                isLinear = false
            } else {
                recyclerView.layoutManager = LinearLayoutManager(context)
                binding.optionsImageView.setImageResource(R.drawable.ic_baseline_grid_view_24)
                isLinear = true
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchDataChange(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (viewModel.isDataEmpty) {
                    Toast.makeText(requireContext(), "Nothing found :( ", Toast.LENGTH_SHORT).show()
                }
                viewModel.onSearchDataChange(query)
                return false
            }
        })

        viewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _recyclerView = null
    }

}