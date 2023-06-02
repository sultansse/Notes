package com.software1t.notes.ui.editNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.software1t.notes.databinding.FragmentEditNoteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditNote : Fragment() {

    private val viewModel: EditNoteViewModel by viewModel { parametersOf(requireActivity().application, noteId) }

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private var noteId: Long = -1
    private var isNewNote = false

    private var _title: TextInputEditText? = null
    private val title get() = _title!!

    private var _desc: TextInputEditText? = null
    private val desc get() = _desc!!

    private var _lastModified: MaterialTextView? = null
    private val lastModified get() = _lastModified!!


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
        _lastModified = binding.lastModified

        getNoteId()
        setTopToolbar()
        setObservers()
        setSaveButton()
        setCopyButton()
        setDeleteButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _title = null
        _desc = null
        _lastModified = null
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
        val args = EditNoteArgs.fromBundle(requireArguments())
        noteId = args.noteId
        if (noteId == -1L) isNewNote = true
    }

    //todo timestamp is shown as long, fix it
    private fun setObservers() {
        viewModel.currentNote.observe(viewLifecycleOwner) {
            if (!isNewNote) {
                title.setText(it.title)
                desc.setText(it.description)
                lastModified.text = it.noteTimestamp.lastModifiedAt.toString()
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
            viewModel.deleteNote()
        }
    }

    private fun setCopyButton() {
        binding.copyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Copied successfully!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            viewModel.copyNote()
        }
    }


}