package com.software1t.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.software1t.notes.data.local.dao.NoteDao
import com.software1t.notes.data.local.entities.NotesEntity

@Database(
    entities = [NotesEntity::class], version = 1, exportSchema = false
)
@TypeConverters(NoteTypeConverters::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}