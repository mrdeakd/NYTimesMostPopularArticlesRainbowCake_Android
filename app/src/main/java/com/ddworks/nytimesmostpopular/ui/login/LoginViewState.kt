package com.ddworks.nytimesmostpopular.ui.login

sealed class LoginViewState

object Loading : LoginViewState()

data class LoginReady(val email: String = "") : LoginViewState()

object TryToLogin : LoginViewState()

object TryToRegister : LoginViewState()

object Register : LoginViewState()

object UserSuccessfullyLoggedIn : LoginViewState()

object LoginError : LoginViewState()

object RegistrationError : LoginViewState()

data class UserSuccessfullyCreated(val email: String) : LoginViewState()