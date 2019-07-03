package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.withIOContext
import com.ddworks.nytimesmostpopular.domain.DomainNews
import com.ddworks.nytimesmostpopular.domain.NewsInteractor
import java.io.IOException
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val newsInteractor: NewsInteractor
) {
    suspend fun getUser(): List<DomainNews> = withIOContext {
        newsInteractor.getNews()
    }

    fun isConnected(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }
}
