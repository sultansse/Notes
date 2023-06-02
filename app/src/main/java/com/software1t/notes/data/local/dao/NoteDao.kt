package com.software1t.notes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.software1t.notes.data.local.entities.NotesEntity
import com.software1t.notes.utils.Constants

@Dao
interface NoteDao {

    @Query("SELECT * FROM ${Constants.NOTES_TABLE} WHERE id = :noteId")
    fun getNote(noteId: Long): LiveData<NotesEntity>

    @Insert
    suspend fun insertNote(notesEntity: NotesEntity)

    @Delete
    suspend fun deleteNote(notesEntity: NotesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(notesEntity: NotesEntity)

    @Query("SELECT * FROM ${Constants.NOTES_TABLE}")
    fun getAllNotes(): LiveData<List<NotesEntity>>

    @Insert
    suspend fun insertAllNotes(notesEntities: List<NotesEntity>)

    @Query("DELETE FROM ${Constants.NOTES_TABLE}")
    suspend fun deleteAllNotes()
}