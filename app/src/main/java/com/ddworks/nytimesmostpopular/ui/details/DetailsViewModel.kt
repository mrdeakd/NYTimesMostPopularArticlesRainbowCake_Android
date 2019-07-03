package com.ddworks.nytimesmostpopular.ui.details

import co.zsmb.rainbowcake.base.JobViewModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val detailsPresenter: DetailsPresenter
) : JobViewModel<DetailsViewState>(DetailsViewState()) {

    fun load() = execute {
        viewState = DetailsViewState(detailsPresenter.getData())
    }

}
