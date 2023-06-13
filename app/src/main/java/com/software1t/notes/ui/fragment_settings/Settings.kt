package com.software1t.notes.ui.fragment_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.software1t.notes.R
import com.software1t.notes.databinding.FragmentSettingsBinding
import com.software1t.notes.ui.MainActivity
import com.software1t.notes.ui.fragment_note_list.SwipeAction
import com.software1t.notes.ui.fragment_note_list.SwipeConfiguration

class Settings : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options = resources.getStringArray(R.array.dropdown_options)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerOptionsLeft.adapter = adapter
        binding.spinnerOptionsRight.adapter = adapter

        binding.spinnerOptionsLeft.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = parent?.getItemAtPosition(position).toString()
                updateSwipeConfiguration(selectedOption, SwipeAction.Archive)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No implementation needed
            }
        }

        binding.spinnerOptionsRight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = parent?.getItemAtPosition(position).toString()
                updateSwipeConfiguration(selectedOption, SwipeAction.Delete)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No implementation needed
            }
        }

    }

    private fun updateSwipeConfiguration(selectedOption: String, swipeAction: SwipeAction) {
        val swipeConfiguration = when (selectedOption) {
            "Archive" -> SwipeConfiguration(swipeToLeftAction = SwipeAction.Archive, swipeToRightAction = SwipeAction.Archive)
            "Delete" -> SwipeConfiguration(swipeToLeftAction = SwipeAction.Delete, swipeToRightAction = SwipeAction.Delete)
            "Custom" -> SwipeConfiguration(swipeToLeftAction = SwipeAction.Custom, swipeToRightAction = SwipeAction.Custom)
            // Add more cases for other options or custom actions
            else -> SwipeConfiguration(swipeToLeftAction = swipeAction, swipeToRightAction = swipeAction)
        }

        (requireActivity() as MainActivity).updateSwipeConfiguration(swipeConfiguration)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
