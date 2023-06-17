package com.software1t.notes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.software1t.notes.utils.Constants

@Entity(tableName = Constants.NOTES_TABLE)
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val lastModifiedTime: Long
)
