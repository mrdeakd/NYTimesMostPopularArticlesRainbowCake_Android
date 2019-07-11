package com.ddworks.nytimesmostpopular.util

import com.google.firebase.auth.FirebaseAuth

object FirebaseHelper {

    fun userIsLoggedIn(): Boolean = FirebaseAuth.getInstance().uid != null
}