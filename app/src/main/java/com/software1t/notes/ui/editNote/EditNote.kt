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
import com.software1t.notes.utils.Extenshions.Companion.getFormattedTimestamp
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditNote : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private var _title: TextInputEditText? = null
    private val title get() = _title!!

    private var _desc: TextInputEditText? = null
    private val desc get() = _desc!!

    private var _lastModified: MaterialTextView? = null
    private val lastModified get() = _lastModified!!


    private var noteId: Long = -1L
    private var isNewNote = false

    private val viewModel: EditNoteViewModel by viewModel {
        parametersOf(
            requireActivity().application,
            noteId
        )
    }

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
        setCurrentNoteData()
        setSaveButton()
        setCopyButton()
        setDeleteButton()


       /* viewModel.noteType.observe(this) { state ->
            when (state) {
                is NoteType.NewNote -> {
                    // Logic for handling a new note state
                }
                is NoteType.ExistingNote -> {
                    // Logic for handling an existing note state
                }
            }
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _title = null
        _desc = null
        _lastModified = null
    }

    private fun getNoteId() {
        noteId = EditNoteArgs.fromBundle(requireArguments()).noteId
        if (noteId == -1L) isNewNote = true
    }

    private fun setTopToolbar() {
        val topToolbar = binding.topToolbar
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(topToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        topToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setCurrentNoteData() {
        viewModel.currentNote.observe(viewLifecycleOwner) { note ->
            // todo correct way
            val defaultTitle = if (isNewNote) "" else note.title
            val defaultDesc = if (isNewNote) "" else note.description
            val lastModifiedTime = if (isNewNote) System.currentTimeMillis() else note.noteTimestamp.lastModifiedAt

            title.setText(defaultTitle)
            desc.setText(defaultDesc)
            lastModified.text = "last changes:\n ${getFormattedTimestamp(lastModifiedTime)}"
        }
    }

    private fun setSaveButton() {
        binding.saveBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Saved successfully!", Toast.LENGTH_SHORT).show()
            viewModel.onClickSave(
                // todo : pass only note
                title.text.toString(),
                desc.text.toString(),
            )
        }
    }

    private fun setDeleteButton() {
        binding.deleteBtn.setOnClickListener {
            viewModel.onDeleteNote()
            findNavController().popBackStack()
        }
    }

    private fun setCopyButton() {
        binding.copyBtn.setOnClickListener {
            // todo correct Toast
            Toast.makeText(requireContext(), "Copied successfully!", Toast.LENGTH_SHORT).show()
            viewModel.onCopyNote()
            findNavController().popBackStack()
        }
    }
}