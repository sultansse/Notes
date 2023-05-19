package com.software1t.notes.ui.edit_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.software1t.notes.MainActivity
import com.software1t.notes.databinding.FragmentEditNoteBinding


class EditNoteFragment : Fragment() {

    private val viewModel: EditNoteViewModel by viewModels(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EditNoteViewModel(
                    application = requireActivity().application, noteId = noteId
                ) as T
            }
        }
    })
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private var noteId: Long = -1
    private var isNewNote = false

    private var _title: TextInputEditText? = null
    private var _desc: TextInputEditText? = null
    private val title get() = _title!!
    private val desc get() = _desc!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _title = binding.titleEditText
        _desc = binding.descEditText

        getNoteId()
        setTopToolbar()
        setObservers()
        setSaveButton()
        setCopyButton()
        setDeleteButton()

    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).fab.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).fab.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _title = null
        _desc = null
    }

    private fun setTopToolbar() {
        val topToolbar = binding.topToolbar
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        topToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getNoteId() {
        noteId = requireArguments().getLong("note_id")
        if (noteId == -1L) isNewNote = true
    }

    private fun setObservers() {
        viewModel.currentNote.observe(viewLifecycleOwner) {
            if (!isNewNote) {
                title.setText(it.title)
                desc.setText(it.description)
            }
        }
    }

    private fun setSaveButton() {
        binding.save.setOnClickListener {
            Toast.makeText(requireContext(), "Added successfully!", Toast.LENGTH_SHORT).show()
            viewModel.onClickSave(
                title.text.toString(),
                desc.text.toString(),
            )
        }
    }

    private fun setDeleteButton() {
        binding.deleteButton.setOnClickListener {
            Toast.makeText(requireContext(), "Deleted successfully!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            viewModel.deleteNote(
//                title.text.toString(),
//                desc.text.toString(),
//                System.currentTimeMillis()
            )
        }
    }

    private fun setCopyButton() {
        binding.copyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Copied successfully!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
//            HomeFragment().recyclerView.smoothScrollToPosition()
            viewModel.copyNote(
//                title.text.toString(),
//                desc.text.toString(),
//                System.currentTimeMillis()
            )
        }
    }


}