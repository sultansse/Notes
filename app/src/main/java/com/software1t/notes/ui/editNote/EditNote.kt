package com.software1t.notes.ui.editNote

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.software1t.notes.databinding.FragmentEditNoteBinding
import com.software1t.notes.utils.Extenshions.Companion.getFormattedTimestamp
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class EditNote : Fragment(), TextWatcher {

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


    private var isTitleChanged = false
    private var isContentChanged = false
    private val autosaveHandler = Handler()
    private val autosaveRunnable = Runnable { saveNote() }
    private val autosaveDelay = 200L // 1 second delay


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

        viewModel.currentNote.observe(viewLifecycleOwner) { note ->
            binding.titleEt.setText(if (isNewNote) "" else note.title)
            binding.descEt.setText(if (isNewNote) "" else note.content)
            binding.lastModifiedTimeTv.text =
                "last changes:\n ${getFormattedTimestamp(if (isNewNote) System.currentTimeMillis() else note.lastModifiedTime)}"

            binding.titleEt.addTextChangedListener(this)
            binding.descEt.addTextChangedListener(this)

//            // Schedule the autosave to run after the delay
//            scheduleAutosave()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // Remove any pending autosave callbacks
        autosaveHandler.removeCallbacks(autosaveRunnable)
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
            saveNote()
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


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // No implementation needed
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // No implementation needed
    }

    override fun afterTextChanged(s: Editable?) {
        scheduleAutosave()
    }

    private fun scheduleAutosave() {
        // Remove any existing autosave callbacks to avoid duplicate callbacks
        autosaveHandler.removeCallbacks(autosaveRunnable)

        val title = binding.titleEt.text.toString()
        val content = binding.descEt.text.toString()

        // Check if it's a new note and the title and content are not changed
        if (isNewNote && title.isEmpty() && content.isEmpty()) {
            return  // Skip autosaving
        }

        // Set the flags based on whether the title and content are changed
        isTitleChanged = (title.isNotEmpty())
        isContentChanged = (content.isNotEmpty())

        // Schedule a new autosave callback after the delay
        autosaveHandler.postDelayed(autosaveRunnable, autosaveDelay)
    }
    //        convertDateStringToMillis(binding.lastModifiedTimeTv.text.toString())
    private fun saveNote() {
        val title = binding.titleEt.text.toString()
        val content = binding.descEt.text.toString()
        val lastModifiedTime = System.currentTimeMillis()

        viewModel.onSaveNote(title, content, lastModifiedTime)
    }
}
