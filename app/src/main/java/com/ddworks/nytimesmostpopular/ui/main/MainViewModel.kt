package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.base.JobViewModel
import com.ddworks.nytimesmostpopular.MainActivity
import com.ddworks.nytimesmostpopular.util.Functions

class MainViewModel(
    private val mainPresenter: MainPresenter
) : JobViewModel<MainViewState>(Loading) {
    fun loadAll() = execute {
        //Idling
        MainActivity.idlingResource.increment()
        checkInternetConnection()
        val previousState = viewState
        viewState = Loading
        viewState = when (previousState) {
            is NewsSearching -> previousState.copy(mainPresenter.getNewsByMatchingString(previousState.searchString))
            else -> NewsLoaded(mainPresenter.getNews())
        }
        //Idling
        MainActivity.idlingResource.decrement()
    }

    fun openDetail(newsId: Int) {
        viewState = NewsDetailedLoaded(newsId)
    }

    fun setStateToDetailPageLoaded() {
        viewState = DetailPageLoaded
    }

    fun changeSearchString(searchString: String) = execute{
        when(viewState){is NewsSearching -> viewState = (viewState as NewsSearching).copy(mainPresenter.getNewsByMatchingString(searchString),searchString)
            is NewsLoaded -> viewState = NewsSearching(mainPresenter.getNewsByMatchingString(searchString),searchString)
        }
    }

    private fun checkInternetConnection(){
        if (!Functions.isConnected()) {
            viewState = NoConnection
        }
    }
}
