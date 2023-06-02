package com.software1t.notes.data.local

import androidx.room.TypeConverter
import com.software1t.notes.data.local.model.NoteTimestamp
import java.text.SimpleDateFormat
import java.util.Locale

class NoteTypeConverters {
    private val dateFormat = SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault())

    @TypeConverter
    fun fromNoteTimestamp(noteTimestamp: NoteTimestamp): String {
        return dateFormat.format(noteTimestamp.lastModifiedAt)
    }

    @TypeConverter
    fun toNoteTimestamp(timestamp: String): NoteTimestamp {
        val currentTime = System.currentTimeMillis()
        val lastModifiedAt = dateFormat.parse(timestamp)?.time ?: currentTime
        return NoteTimestamp(createdAt = currentTime, lastModifiedAt = lastModifiedAt)
    }
}

