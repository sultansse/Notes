package com.software1t.notes.ui.fragment_settings

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.software1t.notes.ui.fragment_settings.swipe_helper.SwipeAction
import com.software1t.notes.ui.fragment_settings.swipe_helper.SwipeConfiguration
import com.software1t.notes.ui.fragment_settings.swipe_helper.SwipeConfigurationCallback
import com.software1t.notes.utils.Constants.Companion.SETTINGS_PREFS
import com.software1t.notes.utils.Constants.Companion.SWIPE_PREF_KEY

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences =
        application.getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE)

    private val _swipesEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
            value = sharedPreferences.getBoolean(SWIPE_PREF_KEY, true)
        }
    val swipesEnabled: LiveData<Boolean> get() = _swipesEnabled

    fun onSwipesEnabledChanged(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(SWIPE_PREF_KEY, isEnabled).apply()
    }

    fun updateSwipeConfiguration(selectedOption: String, swipeAction: SwipeAction) {
        val swipeConfiguration = when (selectedOption) {
            "Archive" -> SwipeConfiguration(swipeAction, swipeAction)
            "Delete" -> SwipeConfiguration(swipeAction, swipeAction)
            "Custom" -> SwipeConfiguration(SwipeAction.Custom, SwipeAction.Custom)
            else -> SwipeConfiguration(swipeAction, swipeAction)
        }

        // Notify the callback interface with the updated swipe configuration
        val callbackInterface = getApplication<Application>() as? SwipeConfigurationCallback
        callbackInterface?.updateSwipeConfiguration(swipeConfiguration)
    }
}
