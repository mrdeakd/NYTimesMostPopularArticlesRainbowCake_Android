package com.ddworks.nytimesmostpopular.ui.details

import co.zsmb.rainbowcake.base.JobViewModel
import com.ddworks.nytimesmostpopular.util.Functions
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val detailsPresenter: DetailsPresenter
) : JobViewModel<DetailsViewState>(Loading) {

    fun loadById(domainNewsId: String) = execute {
        viewState = Loading
        viewState = NewsLoaded(detailsPresenter.getDataById(domainNewsId))
    }

    fun checkInternetConnection(): Boolean{
        if(!Functions.isConnected()){
            viewState = NoConnection
            return false
        }
        return true
    }
}
