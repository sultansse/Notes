package com.software1t.notes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    }

}