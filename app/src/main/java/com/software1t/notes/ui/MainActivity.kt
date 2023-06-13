package com.software1t.notes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.software1t.notes.R
import com.software1t.notes.databinding.ActivityMainBinding
import com.software1t.notes.ui.fragment_note_list.SwipeAction
import com.software1t.notes.ui.fragment_note_list.SwipeConfiguration

class MainActivity : AppCompatActivity() {

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

    fun updateSwipeConfiguration(configuration: SwipeConfiguration) {
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
