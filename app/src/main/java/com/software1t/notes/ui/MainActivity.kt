package com.software1t.notes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.software1t.notes.R
import com.software1t.notes.databinding.ActivityMainBinding
import com.software1t.notes.ui.fragment_settings.swipe_helper.SwipeAction
import com.software1t.notes.ui.fragment_settings.swipe_helper.SwipeConfiguration
import com.software1t.notes.ui.fragment_settings.swipe_helper.SwipeConfigurationCallback

class MainActivity : AppCompatActivity(), SwipeConfigurationCallback {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    lateinit var swipeConfiguration: SwipeConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        swipeConfiguration = SwipeConfiguration(
            swipeToLeftAction = SwipeAction.Archive,
            swipeToRightAction = SwipeAction.Delete
        )
    }

    override fun updateSwipeConfiguration(configuration: SwipeConfiguration) {
        // Implement the logic to update the swipe configuration
        swipeConfiguration = configuration
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
