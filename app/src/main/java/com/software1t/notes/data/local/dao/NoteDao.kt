package com.software1t.notes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.software1t.notes.data.local.model.NoteLocalModel

@Dao
interface NoteDao {
    @Insert
    fun insertNote(noteLocalModel: NoteLocalModel)

    @Insert
    fun insertAllNotes(noteLocalModels: List<NoteLocalModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(noteLocalModel: NoteLocalModel)

    @Delete
    fun deleteNote(noteLocalModel: NoteLocalModel)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<NoteLocalModel>>

    @Query("DELETE FROM notes")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNote(noteId: Long): LiveData<NoteLocalModel>
}
