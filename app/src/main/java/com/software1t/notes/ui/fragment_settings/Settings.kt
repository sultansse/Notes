package com.software1t.notes.ui.fragment_settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.software1t.notes.R
import com.software1t.notes.databinding.FragmentSettingsBinding
import com.software1t.notes.ui.MainActivity
import com.software1t.notes.ui.fragment_note_list.SwipeAction
import com.software1t.notes.ui.fragment_note_list.SwipeConfiguration
import com.software1t.notes.utils.Constants.Companion.SWIPEABILITY_PREF_KEY
import kotlinx.coroutines.launch

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

        binding.switchSwipes.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                val sharedPreferences =
                    requireActivity().getSharedPreferences(SWIPEABILITY_PREF_KEY, Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean("GESTURES", isChecked)
                    .apply()
            }
        }


        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerOptionsLeft.adapter = adapter
        binding.spinnerOptionsRight.adapter = adapter

        binding.spinnerOptionsLeft.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    val selectedOption = parent?.getItemAtPosition(position).toString()
                    updateSwipeConfiguration(
                        selectedOption, SwipeAction.Archive, binding.spinnerOptionsLeft
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // No implementation needed
                }
            }

        binding.spinnerOptionsRight.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    val selectedOption = parent?.getItemAtPosition(position).toString()
                    updateSwipeConfiguration(
                        selectedOption, SwipeAction.Delete, binding.spinnerOptionsRight
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // No implementation needed
                }
            }

    }

    private fun updateSwipeConfiguration(
        selectedOption: String, swipeAction: SwipeAction, spinner: Spinner
    ) {
        val swipeToLeftAction = when (selectedOption) {
            "Archive" -> SwipeAction.Archive
            "Delete" -> SwipeAction.Delete
            "Custom" -> SwipeAction.Custom
            // Add more cases for other options or custom actions
            else -> swipeAction
        }

        val swipeToRightAction = when (selectedOption) {
            "Archive" -> SwipeAction.Archive
            "Delete" -> SwipeAction.Delete
            "Custom" -> SwipeAction.Custom
            // Add more cases for other options or custom actions
            else -> swipeAction
        }

        val swipeConfiguration = SwipeConfiguration(
            swipeToLeftAction = swipeToLeftAction, swipeToRightAction = swipeToRightAction
        )

        (requireActivity() as MainActivity).updateSwipeConfiguration(swipeConfiguration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
