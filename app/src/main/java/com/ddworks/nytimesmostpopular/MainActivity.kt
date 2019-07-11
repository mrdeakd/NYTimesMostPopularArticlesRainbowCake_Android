package com.ddworks.nytimesmostpopular

import android.os.Bundle
import androidx.test.espresso.idling.CountingIdlingResource
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import com.ddworks.nytimesmostpopular.ui.main.MainFragment

class MainActivity : SimpleNavActivity() {

    companion object{
        val idlingResource = CountingIdlingResource("IDLING_RESOURCE")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigator.add(MainFragment())
        }
    }

    fun getidlingResource() = idlingResource
}
