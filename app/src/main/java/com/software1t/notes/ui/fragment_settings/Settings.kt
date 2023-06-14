package com.software1t.notes.ui.fragment_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.software1t.notes.databinding.FragmentSettingsBinding
import com.software1t.notes.ui.fragment_settings.swipe_helper.SwipeAction
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class Settings : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModel {
        parametersOf(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options = listOf("Delete", "Archive", "Custom")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerLeftOptions.adapter = adapter
        binding.spinnerRightOptions.adapter = adapter

        binding.switchSwipes.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSwipesEnabledChanged(isChecked)
        }

        viewModel.swipesEnabled.observe(viewLifecycleOwner) { swipesEnabled ->
            binding.switchSwipes.isChecked = swipesEnabled
        }

        binding.spinnerLeftOptions.onItemSelectedListener =
            createItemSelectedListener(SwipeAction.Archive, binding.spinnerLeftOptions)

        binding.spinnerRightOptions.onItemSelectedListener =
            createItemSelectedListener(SwipeAction.Delete, binding.spinnerRightOptions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createItemSelectedListener(swipeAction: SwipeAction, spinner: Spinner) =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedOption = parent?.getItemAtPosition(position).toString()
                viewModel.updateSwipeConfiguration(selectedOption, swipeAction)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No implementation needed
            }
        }
}

