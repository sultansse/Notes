package com.software1t.notes.utils

class Constants {

    companion object {
        // Room Database
        const val DATABASE_NAME = "notes_database"

        const val NOTES_TABLE = "notes_table"
        const val ARCHIVE_TABLE = "archive_table"
        const val TRASH_TABLE = "trash_table"

        // Notes
        const val NEW_EMPTY_NOTE_ID: Long = 0

        // SharedPreferences
        const val SETTINGS_PREFS = "Settings_prefs"
        const val NOTE_LIST_PREFS = "NoteList_prefs"

        const val SWIPE_PREF_KEY = "swipe_gestures"
        const val LAYOUT_PREF_KEY = "layout_manager"
        const val NOTES_ORDER_KEY = "notes_order"

        // Swipe Actions
        const val SWIPE_ACTION_ARCHIVE = "Archive"
        const val SWIPE_ACTION_DELETE = "Delete"
        const val SWIPE_ACTION_CUSTOM = "Custom"

        // Swipe Directions
        const val SWIPE_DIRECTION_LEFT = "left"
        const val SWIPE_DIRECTION_RIGHT = "right"
    }
}