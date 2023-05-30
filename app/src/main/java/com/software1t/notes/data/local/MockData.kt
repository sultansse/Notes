package com.software1t.notes.data.local

import com.software1t.notes.data.local.model.NoteLocalModel

class MockData {

    val mockNoteLocalModels = mutableListOf(
        NoteLocalModel(0, "Grocery list", "milk, bread, eggs"),
        NoteLocalModel(0, "Reminder", "Call mom at 5pm"),
        NoteLocalModel(0, "Ideas", "New app feature: Voice notes"),
        NoteLocalModel(0, "Meeting with boss", "Discuss performance and career goals"),
        NoteLocalModel(0, "Ideas", "New app feature: Location-based reminders"),
    )
}