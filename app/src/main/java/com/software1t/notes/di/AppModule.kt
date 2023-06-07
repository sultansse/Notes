package com.software1t.notes.di

import com.software1t.notes.domain.useсases.SaveButtonClick
import org.koin.dsl.module

val appModule = module {
    factory { SaveButtonClick(get()) }
}
