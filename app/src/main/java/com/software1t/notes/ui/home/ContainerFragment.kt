package com.software1t.notes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.software1t.notes.R
import com.software1t.notes.databinding.FragmentContainerBinding
import com.software1t.notes.ui.home.recyclerview.NotesAdapter

class ContainerFragment : Fragment() {

    private lateinit var viewModel: ContainerFragmentViewModel
    private var _binding: FragmentContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ContainerFragmentViewModel::class.java]


        val adapter = NotesAdapter()
        binding.recyclerView.adapter = adapter;
        viewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        var linearOrGrid = 0

        binding.optionsImageView.setOnClickListener {
            if (linearOrGrid == 0) {
                binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
                binding.optionsImageView.setImageResource(R.drawable.ic_outline_linear_view_24)
                linearOrGrid = 1
            } else {
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.optionsImageView.setImageResource(R.drawable.ic_baseline_grid_view_24)
                linearOrGrid = 0
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchDataChange(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (viewModel.isEmpty) {
                    Toast.makeText(requireContext(), "Nothing found :( ", Toast.LENGTH_SHORT).show()
                }
                viewModel.onSearchDataChange(query)
                return false
            }
        })

    }

}