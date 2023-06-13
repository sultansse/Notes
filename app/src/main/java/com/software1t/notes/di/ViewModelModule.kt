package com.software1t.notes.di

import android.app.Application
import com.software1t.notes.ui.fragment_edit_note.EditNoteViewModel
import com.software1t.notes.ui.fragment_note_list.NotesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (application: Application) ->
        NotesListViewModel(application, get())
    }
    viewModel { (application: Application, noteId: Long) ->
        EditNoteViewModel(application, noteId, get())
    }
}