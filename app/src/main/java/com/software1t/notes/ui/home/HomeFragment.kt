package com.software1t.notes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    private var isLayoutManagerChanged = false

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


        viewModel.isLinear.observe(viewLifecycleOwner) {
            isLayoutManagerChanged = it
        }
//            // todo bugs:
//            // захожу в notefragment и выхожу он говорит что layoutmanager is already attached.
//            // потому после того как экран поменяется через notefragment - он попытается по новой положить layoutmanager в recyclerview
//            //
//            //удаляю предпоследний элемент  после захожу уже в след элемент - кидает indexoutofbound

        viewModel.layoutManager.observe(viewLifecycleOwner) {
            if (isLayoutManagerChanged) {
                recyclerView.layoutManager = it
            }
        }

        viewModel.layoutManagerIcon.observe(viewLifecycleOwner) {
            binding.layoutManagerIconImageView.setImageResource(it)
        }
//
        binding.layoutManagerIconImageView.setOnClickListener {
            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()

            viewModel.onLayoutManagerChange()
        }

/*        var isLinear = true
        binding.layoutManagerIconImageView.setOnClickListener {
            if (isLinear) {
                recyclerView.layoutManager = GridLayoutManager(context, 2)
                binding.layoutManagerIconImageView.setImageResource(R.drawable.ic_outline_linear_view_24)
                isLinear = false
            } else{
                recyclerView.layoutManager = LinearLayoutManager(context)
                binding.layoutManagerIconImageView.setImageResource(R.drawable.ic_baseline_grid_view_24)
                isLinear = true
            }

        }*/

        viewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchDataChange(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.isDataEmpty.observe(viewLifecycleOwner) {
                    if (it) Toast.makeText(
                        requireContext(), "Nothing found :( ", Toast.LENGTH_SHORT
                    ).show()
                }
                viewModel.onSearchDataChange(query)
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _recyclerView = null
    }

}