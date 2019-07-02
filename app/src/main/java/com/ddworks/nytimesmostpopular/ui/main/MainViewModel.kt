package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.base.JobViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainPresenter: MainPresenter
) : JobViewModel<MainViewState>(MainViewState()) {

    fun load() = execute {
        viewState = MainViewState(mainPresenter.getUser())
    }

}
