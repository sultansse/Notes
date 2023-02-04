package com.software1t.notes.ui.edit_note

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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
    private lateinit var title: TextInputEditText
    private lateinit var desc: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)

        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = binding.titleEditText
        desc = binding.descEditText

        getNoteId()
        setTopToolbar()
        setObservers()
        setSubmitButton()
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).fab.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).fab.visibility = View.VISIBLE
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

    private fun setSubmitButton() {
        binding.submit.setOnClickListener {
            Toast.makeText(requireContext(), "added successfully!", Toast.LENGTH_SHORT).show()
            viewModel.onClickSubmit(
                title.text.toString(),
                desc.text.toString(),
            )
        }
    }
}