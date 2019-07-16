package com.ddworks.nytimesmostpopular

import android.os.Bundle
import androidx.test.espresso.idling.CountingIdlingResource
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import com.ddworks.nytimesmostpopular.ui.login.LoginFragment
import com.ddworks.nytimesmostpopular.ui.main.MainFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : SimpleNavActivity() {

    override val defaultEnterAnim: Int = R.anim.slide_in_right
    override val defaultExitAnim: Int = R.anim.slide_out_left
    override val defaultPopEnterAnim: Int = R.anim.slide_in_left
    override val defaultPopExitAnim: Int = R.anim.slide_out_right

    companion object {
        val idlingResource = CountingIdlingResource("IDLING_RESOURCE")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            if (userIsLoggedIn())
                navigator.add(MainFragment())
            else
                navigator.add(LoginFragment())
        }
    }

    private fun userIsLoggedIn(): Boolean = FirebaseAuth.getInstance().uid != null

    fun getidlingResource() = idlingResource
}
