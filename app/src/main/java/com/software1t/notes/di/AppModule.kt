package com.software1t.notes.di

import android.app.Application
import com.software1t.notes.ui.editNote.EditNoteViewModel
import com.software1t.notes.ui.notesList.NotesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val AppModule = module {
    viewModel { NotesListViewModel(get()) }
    factory { (application: Application, noteId: Long) -> EditNoteViewModel(application, noteId) }
}

//val useCaseModule = module {
//    factory { NewNoteUseCase(get()) }
//    factory { GetNotesUseCase(get()) }
//    factory { FetchNoteByIdUseCase(get()) }
//}