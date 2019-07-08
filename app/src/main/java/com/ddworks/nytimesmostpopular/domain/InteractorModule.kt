package com.ddworks.nytimesmostpopular.domain

import org.koin.dsl.module

val InteractorModule = module {
    factory { NewsInteractor(get(), get()) }
}