package com.software1t.notes.data.local

import androidx.room.*
import com.software1t.notes.utils.Constants

@Dao
interface NoteDao {
    @Query("SELECT * FROM ${Constants.NOTES_TABLE} WHERE id = :noteId")
    fun getNoteById(noteId: Long): NotesEntity

    @Insert
    suspend fun insertNote(notesEntity: NotesEntity)

    @Delete
    suspend fun deleteNote(notesEntity: NotesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(notesEntity: NotesEntity)

    @Query("SELECT * FROM ${Constants.NOTES_TABLE}")
    fun getAllNotes(): List<NotesEntity>

    @Insert
    suspend fun insertAllNotes(notesEntities: List<NotesEntity>)

    @Query("DELETE FROM ${Constants.NOTES_TABLE}")
    suspend fun deleteAllNotes()
}