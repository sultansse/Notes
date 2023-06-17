package com.software1t.notes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.software1t.notes.data.local.entities.NotesEntity
import com.software1t.notes.utils.Constants

@Dao
interface TrashDao {
    @Query("SELECT * FROM ${Constants.TRASH_TABLE} WHERE id = :noteId")
    fun getNoteById(noteId: Long): LiveData<NotesEntity>

    @Query("DELETE FROM ${Constants.TRASH_TABLE} WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)

    @Insert
    suspend fun insertNote(trashEntity: NotesEntity)

    @Delete
    suspend fun deleteNote(trashEntity: NotesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(trashEntity: NotesEntity)

    @Query("SELECT * FROM ${Constants.TRASH_TABLE}")
    fun getAllNotes(): LiveData<List<NotesEntity>>

    @Insert
    suspend fun insertAllNotes(trashEntities: List<NotesEntity>)

    @Query("DELETE FROM ${Constants.TRASH_TABLE}")
    suspend fun deleteAllNotes()
}