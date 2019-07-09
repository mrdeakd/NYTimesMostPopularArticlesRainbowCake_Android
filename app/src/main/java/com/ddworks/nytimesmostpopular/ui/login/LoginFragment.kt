package com.ddworks.nytimesmostpopular.ui.login

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.koin.getViewModelFromFactory
import com.ddworks.nytimesmostpopular.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b_login.setOnClickListener{

        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun render(viewState: LoginViewState) {
        when (viewState) {
            is Logged -> {

            }
            is Login -> {

            }
            is Reg -> {

            }
        }
    }

}
