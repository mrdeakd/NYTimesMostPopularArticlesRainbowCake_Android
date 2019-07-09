package com.ddworks.nytimesmostpopular.ui.login

import co.zsmb.rainbowcake.base.JobViewModel

class LoginViewModel(
    private val loginPresenter: LoginPresenter
) : JobViewModel<LoginViewState>(Login) {

    fun login() = execute {
        viewState = Logged
    }

    fun reg() = execute {
        viewState = Reg
    }

}
