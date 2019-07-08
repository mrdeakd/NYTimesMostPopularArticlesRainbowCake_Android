package com.ddworks.nytimesmostpopular

import android.app.Application
import co.zsmb.rainbowcake.config.Loggers
import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.timber.TIMBER
import com.ddworks.nytimesmostpopular.data.disk.DatabaseModule
import com.ddworks.nytimesmostpopular.data.network.NetworkModule
import com.ddworks.nytimesmostpopular.di.UIModule
import com.ddworks.nytimesmostpopular.domain.InteractorModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class NYTimesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NYTimesApplication)
            modules(listOf(UIModule, InteractorModule, NetworkModule, DatabaseModule))
        }

        rainbowCake {
            logger = Loggers.TIMBER
            isDebug = BuildConfig.DEBUG
        }

        Timber.plant(Timber.DebugTree())
    }
}