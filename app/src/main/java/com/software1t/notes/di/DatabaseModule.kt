package com.software1t.notes.di

import androidx.room.Room
import com.software1t.notes.data.local.NotesDatabase
import com.software1t.notes.data.repository.NotesRepositoryImpl
import com.software1t.notes.domain.repository.NotesRepository
import com.software1t.notes.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NotesDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    single { get<NotesDatabase>().noteDao() }
    single<NotesRepository> { NotesRepositoryImpl(get()) }
}
