package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.base.JobViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainPresenter: MainPresenter
) : JobViewModel<MainViewState>(Loading) {
    fun load() = execute {
        if (!mainPresenter.isConnected()) {
            viewState = NoConnection
        } else {
            viewState = Loading
            viewState = NewsLoaded(mainPresenter.getUser())
        }

    }
}
