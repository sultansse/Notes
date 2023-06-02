package com.software1t.notes.domain.useсases

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.software1t.notes.R

class NavigationDrawerHelper(private val activity: AppCompatActivity) {
    private val drawerLayout: DrawerLayout = activity.findViewById(R.id.drawerLayout)
    private val navView: NavigationView = activity.findViewById(R.id.navView)

    fun setupNavigationDrawer() {
        setupMenuImageView()
        setupNavigationItemSelected()
    }

    private fun setupMenuImageView() {
        val menuImageView: ImageView = activity.findViewById(R.id.menu_imageView)

        menuImageView.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupNavigationItemSelected() {
        val navController = activity.findNavController(R.id.nav_host_fragment_activity_main)

        navView.setNavigationItemSelectedListener { menuItem ->
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}
