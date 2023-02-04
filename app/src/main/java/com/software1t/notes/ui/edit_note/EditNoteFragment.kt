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
import com.google.android.material.textfield.TextInputEditText
import com.software1t.notes.MainActivity
import com.software1t.notes.databinding.FragmentEditNoteBinding


class EditNoteFragment : Fragment() {

    private val viewModel: EditNoteViewModel by viewModels()
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private var noteId: Long = -1
    private lateinit var title: TextInputEditText
    private lateinit var desc: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = binding.titleEditText
        desc = binding.descEditText

        noteId = requireArguments().getLong("note_id")
        if (noteId != -1L) {
            viewModel.getNote(noteId).observe(viewLifecycleOwner) {
                title.setText(it.title)
                desc.setText(it.description)
            }
        }

        val topToolbar = binding.topToolbar
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        topToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // todo is viewModel.insertNote() good practice for mvvm?
        binding.submit.setOnClickListener {
            Toast.makeText(requireContext(), "added successfully!", Toast.LENGTH_SHORT).show()
            if (noteId == -1L) {
                viewModel.insertNote(
                    title.text.toString(),
                    desc.text.toString(),
                )
            } else {
                viewModel.updateNote(
                    noteId,
                    title.text.toString(),
                    desc.text.toString(),
                )
            }
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