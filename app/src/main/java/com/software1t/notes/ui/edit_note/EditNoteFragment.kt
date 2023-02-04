package com.software1t.notes.ui.edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.software1t.notes.MainActivity
import com.software1t.notes.databinding.FragmentEditNoteBinding


class EditNoteFragment : Fragment() {

    private val viewModel: EditNoteViewModel by viewModels()
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private var noteId: Long = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = requireArguments().getLong("note_id")
        if (noteId != -1L) {
            viewModel.getNote(noteId).observe(viewLifecycleOwner) {
                binding.titleEditText.setText(it.title)
                binding.descEditText.setText(it.description)
            }
        }

        val topToolbar = binding.topToolbar
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        topToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.submit.setOnClickListener {
            Toast.makeText(requireContext(), "added successfully!", Toast.LENGTH_SHORT).show()
            viewModel.submitToDatabase(
                binding.titleEditText.text.toString(),
                binding.descEditText.text.toString()
            )
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).fab.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).fab.visibility = View.VISIBLE
    }


}