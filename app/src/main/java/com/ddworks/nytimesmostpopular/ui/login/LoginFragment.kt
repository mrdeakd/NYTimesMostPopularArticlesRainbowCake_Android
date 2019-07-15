package com.ddworks.nytimesmostpopular.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.exhaustive
import co.zsmb.rainbowcake.koin.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.ddworks.nytimesmostpopular.R
import com.ddworks.nytimesmostpopular.ui.main.MainFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login_layout.*
import kotlinx.android.synthetic.main.fragment_register_layout.*

class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_login

    private object Flipper {
        const val LOADING = 0
        const val LOGIN = 1
        const val REGISTER = 2
    }

    override fun onEvent(event: OneShotEvent) {
        when(event) {
            is LoginViewModel.NoInternetEvent -> Toast.makeText(context,"Nincs internet",Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    override fun onStart() {
        super.onStart()
        viewModel.setLoginScreen()
    }

    override fun render(viewState: LoginViewState) {
        loginProgressBar.visibility = View.INVISIBLE
        registrationProgressBar.visibility = View.INVISIBLE

        when(viewState) {
            is Loading -> {
                loginViewFlipper.displayedChild = Flipper.LOADING
            }
            is LoginReady -> {
                loginViewFlipper.displayedChild = Flipper.LOGIN
                setEmailText(viewState.email)
            }
            is TryToLogin -> {
                loginProgressBar.visibility = View.VISIBLE
            }
            is Register -> {
                clearRegistrationFields()
                loginViewFlipper.displayedChild = Flipper.REGISTER
            }
            is TryToRegister -> {
                registrationProgressBar.visibility = View.VISIBLE
            }
            is LoginError -> {
                loginFailedHandler()
            }
            is UserSuccessfullyLoggedIn -> {
                navigator!!.replace(MainFragment())
            }
            is RegistrationError -> {
                registrationFailedHandler()
            }
            is UserSuccessfullyCreated -> {
                viewModel.setLoginScreen(viewState.email)
            }
        }.exhaustive
    }

    private fun registrationFailedHandler() {
        Snackbar.make(loginViewFlipper, getString(R.string.registration_failed), Snackbar.LENGTH_LONG).apply {
            this.setAction(getString(R.string.ok)) { this.dismiss() }
        }.show()
        emailTextInputLayout.error = getString(R.string.this_email_already_exists)
        registerPassword.setText("")
    }

    private fun setEmailText(email: String) {
        loginPasswordTextInputLayout.isHintAnimationEnabled = email != ""
        loginEmailAddress.setText(email)
        loginPassword.setText("")
    }

    private fun loginFailedHandler() {
        Snackbar.make(loginViewFlipper, getString(R.string.login_failed), Snackbar.LENGTH_LONG).apply {
            this.setAction(getString(R.string.ok)) { this.dismiss() }
        }.show()
        loginEmailTextInputLayout.error = getString(R.string.incorrect_email_or_password)
        loginPasswordTextInputLayout.error = getString(R.string.incorrect_email_or_password)
        loginPassword.setText("")
    }

    private fun clearRegistrationFields() {
        registerName.setText("")
        registerEmailAddress.setText("")
        registerPassword.setText("")
        loginViewFlipper.requestFocus()
    }

    private fun setupButtons() {
        tvRegister.setOnClickListener {
            viewModel.register()
        }

        tvLogin.setOnClickListener {
            viewModel.setLoginScreen()
        }

        buttonLogin.setOnClickListener {
            viewModel.tryToLogin(loginEmailAddress.text.toString(), loginPassword.text.toString())
        }

        buttonRegister.setOnClickListener {
            viewModel.tryToRegister(registerEmailAddress.text.toString(), registerPassword.text.toString())
        }
    }
}
