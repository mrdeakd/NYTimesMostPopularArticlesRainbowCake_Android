package com.ddworks.nytimesmostpopular.util

import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

object Functions {
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

    private var isRunningTest: AtomicBoolean? = null

    @Synchronized
    fun isRunningTest(): Boolean {
        if (null == isRunningTest) {
            val istest: Boolean = try {
                Class.forName("com.ddworks.nytimesmostpopular.UITest")
                true
            } catch (e: ClassNotFoundException) {
                false
            }

            isRunningTest = AtomicBoolean(istest)
        }

        return isRunningTest!!.get()
    }
}