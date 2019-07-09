package com.ddworks.nytimesmostpopular.ui.login

sealed class LoginViewState

object Login : LoginViewState()

object Reg : LoginViewState()

object Logged : LoginViewState()
