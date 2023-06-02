package com.software1t.notes.data.local.model

data class NoteStatus(
    val isFavorite: Boolean = false,
    val isArchived: Boolean = false,
    val isTrashed: Boolean = false
)
