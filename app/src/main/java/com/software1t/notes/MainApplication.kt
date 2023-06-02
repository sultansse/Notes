package com.software1t.notes

import android.app.Application
import com.software1t.notes.di.databaseModule
import com.software1t.notes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(viewModelModule, databaseModule))
        }
    }
}