package com.ddworks.nytimesmostpopular.di

import com.ddworks.nytimesmostpopular.ui.details.DetailsPresenter
import com.ddworks.nytimesmostpopular.ui.details.DetailsViewModel
import com.ddworks.nytimesmostpopular.ui.main.MainPresenter
import com.ddworks.nytimesmostpopular.ui.main.MainViewModel
import org.koin.dsl.module

val UIModule = module {
    factory { MainPresenter(get()) }
    factory { DetailsPresenter(get()) }
    factory { MainViewModel(get()) }
    factory { DetailsViewModel(get()) }
}