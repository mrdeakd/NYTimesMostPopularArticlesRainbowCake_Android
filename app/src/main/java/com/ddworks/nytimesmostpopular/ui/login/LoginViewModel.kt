package com.ddworks.nytimesmostpopular.ui.login

import co.zsmb.rainbowcake.base.JobViewModel
import com.ddworks.nytimesmostpopular.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : JobViewModel<LoginViewState>(Loading) {

    fun setLoginScreen(email: String = "") {
        viewState = LoginReady(email)
    }

    fun register() {
        viewState = Register
    }

    fun tryToLogin(email: String, password: String) {
        //Idling
        MainActivity.idlingResource.increment()
        viewState = TryToLogin
        val auth = FirebaseAuth.getInstance()

        if (email.isEmpty() || password.isEmpty()) {
            viewState = RegistrationError
        }
        else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewState = UserSuccessfullyLoggedIn
                    } else {
                        viewState = LoginError
                    }
                    //Idling
                    MainActivity.idlingResource.decrement()
                }
        }
    }

    fun tryToRegister(email: String, password: String) {
        //Idling
        MainActivity.idlingResource.increment()
        viewState = TryToRegister
        val auth = FirebaseAuth.getInstance()

        if (email.isEmpty() || password.isEmpty()) {
            viewState = RegistrationError
        }
        else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser!!
                        viewState = UserSuccessfullyCreated(user.email!!)
                    } else {
                        viewState = RegistrationError
                    }
                    //Idling
                    MainActivity.idlingResource.decrement()
                }
        }
    }
}