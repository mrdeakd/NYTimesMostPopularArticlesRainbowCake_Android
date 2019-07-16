package com.ddworks.nytimesmostpopular.domain

import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource
import com.ddworks.nytimesmostpopular.util.Functions.isRunningTest
import org.koin.dsl.module

val InteractorModule = module {
    factory { provideInteractor(get(), get()) }
}

fun provideInteractor(diskDS: DiskDataSource, networkDS: NetworkDataSource) = if (isRunningTest()) NewsInteractorMock() else NewsInteractorImp(networkDS, diskDS)
