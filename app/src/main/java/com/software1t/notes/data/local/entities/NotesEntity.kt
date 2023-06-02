package com.software1t.notes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.software1t.notes.data.local.model.NoteTimestamp
import com.software1t.notes.utils.Constants

@Entity(tableName = Constants.NOTES_TABLE)
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val noteTimestamp: NoteTimestamp,
//    val noteStatus: NoteStatus = todo should be added default value here (не дефолтные валуе внутри а прям сюда дефолт валуе)
)
