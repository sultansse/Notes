package com.software1t.notes.ui.editNote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.software1t.notes.databinding.FragmentEditNoteBinding
import com.software1t.notes.utils.Extenshions.Companion.convertDateStringToMillis
import com.software1t.notes.utils.Extenshions.Companion.getFormattedTimestamp
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditNote : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditNoteViewModel by viewModel {
        parametersOf(
            requireActivity().application,
            noteId
        )
    }

    private var noteId: Long = -1L
    private var isNewNote = (noteId == -1L)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        noteId = EditNoteArgs.fromBundle(requireArguments()).noteId
        isNewNote = (noteId == -1L)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTopToolbar()
        setSaveButton()
        setCopyButton()
        setDeleteButton()

//  todo title_et add functionality NOT able to enter new line,
//   only submit\go\focus to next element\content

//        binding.titleEt.setOnKeyListener { _, keyCode, event ->
//            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//                // Move the focus to the desc EditText
//                binding.contentEt.requestFocus()
//                return@setOnKeyListener true
//            }
//            return@setOnKeyListener false
//        }


        viewModel.currentNote.observe(viewLifecycleOwner) { note ->
            binding.titleEt.setText(if (isNewNote) "" else note.title)
            binding.contentEt.setText(if (isNewNote) "" else note.content)
            binding.lastModifiedTimeTv.text =
                "last changes:\n ${getFormattedTimestamp(if (isNewNote) System.currentTimeMillis() else note.lastModifiedTime)}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun setSaveButton() {
        binding.saveBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Saved successfully!", Toast.LENGTH_SHORT).show()
            viewModel.onSaveNote(
                binding.titleEt.text.toString(),
                binding.contentEt.text.toString(),
                convertDateStringToMillis(binding.lastModifiedTimeTv.text.toString())
            )
            findNavController().popBackStack()
        }
    }

    private fun setDeleteButton() {
        binding.deleteBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Deleted successfully!", Toast.LENGTH_SHORT).show()
            viewModel.onDeleteNote()
            findNavController().popBackStack()
        }
    }

    private fun setCopyButton() {
        binding.copyBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Copied successfully!", Toast.LENGTH_SHORT).show()
            viewModel.onCopyNote()
            findNavController().popBackStack()
        }
    }
}