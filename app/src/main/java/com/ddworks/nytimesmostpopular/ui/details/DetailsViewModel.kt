package com.ddworks.nytimesmostpopular.ui.details

import co.zsmb.rainbowcake.base.JobViewModel
import com.ddworks.nytimesmostpopular.MainActivity
import com.ddworks.nytimesmostpopular.util.Functions

class DetailsViewModel(
    private val detailsPresenter: DetailsPresenter
) : JobViewModel<DetailsViewState>(Loading) {

    fun loadById(domainNewsId: String) = execute {
        // Idling
        MainActivity.idlingResource.increment()
        viewState = Loading
        viewState = NewsLoaded(detailsPresenter.getDataById(domainNewsId))
        // Idling
        MainActivity.idlingResource.decrement()
    }

    fun checkInternetConnection(): Boolean {
        if (!Functions.isConnected()) {
            viewState = NoConnection
            return false
        }
        return true
    }
}
