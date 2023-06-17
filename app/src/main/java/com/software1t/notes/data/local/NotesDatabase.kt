package com.software1t.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.software1t.notes.data.local.dao.NoteDao
import com.software1t.notes.data.local.entities.NotesEntity

@Database(entities = [NotesEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}