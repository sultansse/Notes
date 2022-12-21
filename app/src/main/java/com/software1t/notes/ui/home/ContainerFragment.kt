package com.software1t.notes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
                linearOrGrid = 1
            }else{
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                linearOrGrid = 0
            }
        }

    }

}