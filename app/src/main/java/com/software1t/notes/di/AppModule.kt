package com.software1t.notes.di

import org.koin.dsl.module

val AppModule = module {

}
//    viewModel { PostsViewModel(get()) }
//
//    single { createGetPostsUseCase(get()) }
//
//    single { createPostRepository(get()) }
//
//
//
//
//val dbModule = module {
//    single {
//        NotesDatabase.getInstance(androidContext())
//    }
//}
//
//val viewModelModule = module {
//    factory { NewNoteViewModel(get()) }
//    factory { NotesListViewModel(get()) }
//    factory { NoteViewViewModel(get()) }
//}
//
//val useCaseModule = module {
//    factory { NewNoteUseCase(get()) }
//    factory { GetNotesUseCase(get()) }
//    factory { FetchNoteByIdUseCase(get()) }
//}
//
//val repositoryModule = module {
//    single<NotesRepository> {
//        NotesRepositoryImpl(get())
//    }
//}