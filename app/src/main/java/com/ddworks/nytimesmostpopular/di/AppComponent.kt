package com.ddworks.nytimesmostpopular.di

import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import co.zsmb.rainbowcake.dagger.RainbowCakeModule
import com.ddworks.nytimesmostpopular.data.network.NYTimesNetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RainbowCakeModule::class,
        ViewModelModule::class,
        ApplicationModule::class,
        NYTimesNetworkModule::class
    ]
)
interface AppComponent : RainbowCakeComponent