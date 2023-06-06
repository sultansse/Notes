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
import java.text.SimpleDateFormat
import java.util.Locale

class EditNote : Fragment() {

    private val viewModel: EditNoteViewModel by viewModel {
        parametersOf(
            requireActivity().application,
            noteId
        )
    }

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

        _title = binding.titleEt
        _desc = binding.descEt
        _lastModified = binding.modifiedTimeTv

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

    private fun getNoteId() {
        val args = EditNoteArgs.fromBundle(requireArguments())
        noteId = args.noteId
        if (noteId == -1L) isNewNote = true
    }

    private fun setTopToolbar() {
        val topToolbar = binding.topToolbar
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        topToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setObservers() {
        viewModel.currentNote.observe(viewLifecycleOwner) {
            if (!isNewNote) {
                title.setText(it.title)
                desc.setText(it.description)
                val currentTimeMillis = it.noteTimestamp.lastModifiedAt
                val dateFormat = SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault())
                val formattedTimestamp = dateFormat.format(currentTimeMillis)
                lastModified.text = "last changes:\n $formattedTimestamp"
            }
        }
    }

    private fun setSaveButton() {
        binding.saveBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Saved successfully!", Toast.LENGTH_SHORT).show()
            viewModel.onClickSave(
                title.text.toString(),
                desc.text.toString(),
            )
            findNavController().popBackStack()
        }
    }

    private fun setDeleteButton() {
        binding.deleteBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Deleted successfully!", Toast.LENGTH_SHORT).show()
            viewModel.deleteNote()
            findNavController().popBackStack()
        }
    }

    private fun setCopyButton() {
        binding.copyBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Copied successfully!", Toast.LENGTH_SHORT).show()
            viewModel.copyNote()
            findNavController().popBackStack()
        }
    }
}