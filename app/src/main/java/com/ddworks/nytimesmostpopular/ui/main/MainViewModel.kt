package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.base.JobViewModel
import com.ddworks.nytimesmostpopular.util.Functions

class MainViewModel(
    private val mainPresenter: MainPresenter
) : JobViewModel<MainViewState>(Loading) {
    fun loadAll() = execute {
        checkInternetConnection()
        val previousState = viewState
        viewState = Loading
        viewState = when (previousState) {
            is NewsSearching -> previousState.copy(mainPresenter.getNewsByMatchingString(previousState.searchString))
            else -> NewsLoaded(mainPresenter.getNews())
        }
    }

    fun openDetail(newsId: Int) {
        viewState = NewsDetailedLoaded(newsId)
    }

    fun setStateToDetailPageLoaded() {
        viewState = DetailPageLoaded
    }

    fun changeSearchString(searchString: String) = execute{
        when(viewState){
            is NewsSearching -> viewState = (viewState as NewsSearching).copy(dataList = mainPresenter.getNewsByMatchingString(searchString))
            is NewsLoaded -> viewState = NewsSearching(mainPresenter.getNewsByMatchingString(searchString),searchString)
        }
    }

    private fun checkInternetConnection(){
        if (!Functions.isConnected()) {
            viewState = NoConnection
        }
    }
}
