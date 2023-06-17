package com.software1t.notes.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.software1t.notes.data.local.entities.ArchiveEntity
import com.software1t.notes.utils.Constants

@Dao
interface ArchiveDao {
    @Query("SELECT * FROM ${Constants.ARCHIVE_TABLE} WHERE id = :noteId")
    fun getNoteById(noteId: Long): LiveData<ArchiveEntity>

    @Query("DELETE FROM ${Constants.ARCHIVE_TABLE} WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)

    @Insert
    suspend fun insertNote(archiveEntity: ArchiveEntity)

    @Delete
    suspend fun deleteNote(archiveEntity: ArchiveEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(archiveEntity: ArchiveEntity)

    @Query("SELECT * FROM ${Constants.ARCHIVE_TABLE}")
    fun getAllNotes(): LiveData<List<ArchiveEntity>>

    @Insert
    suspend fun insertAllNotes(archiveEntities: List<ArchiveEntity>)

    @Query("DELETE FROM ${Constants.ARCHIVE_TABLE}")
    suspend fun deleteAllNotes()
}