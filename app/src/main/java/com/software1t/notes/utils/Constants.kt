package com.software1t.notes.utils

class Constants {

    companion object {
        // Room Database
        const val DATABASE_NAME = "notes_database"
        const val NOTES_TABLE = "notes_table"

        // Notes
        const val NEW_EMPTY_NOTE_ID: Long = 0

        // SharedPreferences
        const val SETTINGS_PREFS = "SETTINGS_PREFS"
        const val NOTE_LIST_PREFS = "NOTE_LIST_PREFS"

        const val LAYOUT_PREF_KEY = "LAYOUT_MANAGER"
        const val SWIPE_PREF_KEY = "SWIPE_GESTURES"
    }
}