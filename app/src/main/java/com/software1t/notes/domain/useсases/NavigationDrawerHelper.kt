package com.software1t.notes.domain.useсases

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
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
        val menuImageBtn: AppCompatImageButton = activity.findViewById(R.id.menu_btn)

        menuImageBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupNavigationItemSelected() {
        val navController = activity.findNavController(R.id.nav_host_fragment)

        navView.setNavigationItemSelectedListener { menuItem ->
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}