package com.ddworks.nytimesmostpopular

import android.os.Bundle
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import com.ddworks.nytimesmostpopular.ui.main.MainFragment

class MainActivity : SimpleNavActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigator.add(MainFragment())
        }
    }
}
