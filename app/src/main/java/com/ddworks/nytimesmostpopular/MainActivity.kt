package com.ddworks.nytimesmostpopular

import android.os.Bundle
import androidx.test.espresso.idling.CountingIdlingResource
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import com.ddworks.nytimesmostpopular.ui.login.LoginFragment
import com.ddworks.nytimesmostpopular.ui.main.MainFragment
import com.ddworks.nytimesmostpopular.util.Functions.isRunningTest
import com.google.firebase.auth.FirebaseAuth

class MainActivity : SimpleNavActivity() {

    companion object{
        val idlingResource = CountingIdlingResource("IDLING_RESOURCE")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            if (userIsLoggedIn() || isRunningTest())
                navigator.add(MainFragment())
            else
                navigator.add(LoginFragment())
        }
    }

    private fun userIsLoggedIn(): Boolean = FirebaseAuth.getInstance().uid != null

    fun getidlingResource() = idlingResource
}
