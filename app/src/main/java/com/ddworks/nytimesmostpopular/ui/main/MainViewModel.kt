package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.base.JobViewModel
import com.ddworks.nytimesmostpopular.MainActivity
import com.ddworks.nytimesmostpopular.util.Functions

class MainViewModel(
    private val mainPresenter: MainPresenter
) : JobViewModel<MainViewState>(Loading) {
    fun loadAll() = execute {
        // Idling
        MainActivity.idlingResource.increment()
        checkInternetConnection()
        val previousState = viewState
        viewState = Loading
        viewState = when (previousState) {
            is NewsSearching -> previousState.copy(
                mainPresenter.getNewsByMatchingString(
                    previousState.searchString,
                    ""
                )
            )
            is NewsFilter -> previousState.copy(
                mainPresenter.getNewsByMatchingString(
                    previousState.searchString,
                    previousState.filter
                )
            )
            else -> NewsLoaded(mainPresenter.getNews())
        }
        // Idling
        MainActivity.idlingResource.decrement()
    }

    fun openDetail(newsId: Int) {
        viewState = NewsDetailedLoaded(newsId)
    }

    fun setStateToDetailPageLoaded() {
        viewState = DetailPageLoaded
    }

    fun changeSearchString(searchString: String) = execute {
        when (viewState) {
            is NewsFilter -> viewState =
                (viewState as NewsFilter).copy(
                    mainPresenter.getNewsByMatchingString(
                        searchString,
                        (viewState as NewsFilter).filter
                    ), searchString = searchString
                )
            is NewsSearching -> viewState =
                (viewState as NewsSearching).copy(mainPresenter.getNewsByMatchingString(searchString, ""), searchString = searchString)
            is NewsLoaded -> viewState =
                NewsSearching(mainPresenter.getNewsByMatchingString(searchString, ""), searchString = searchString)
        }
    }

    private fun checkInternetConnection() {
        if (!Functions.isConnected()) {
            viewState = NoConnection
        }
    }

    fun sortBy(filter: String) = execute {
        when (viewState) {
            is NewsFilter -> viewState = (viewState as NewsFilter).copy(
                mainPresenter.getNewsByMatchingString(
                    (viewState as NewsFilter).searchString,
                    filter
                ), filter = filter
            )
            is NewsSearching -> viewState = NewsFilter(
                mainPresenter.getNewsByMatchingString((viewState as NewsSearching).searchString, filter),
                (viewState as NewsSearching).searchString, filter
            )
            is NewsLoaded -> viewState = NewsFilter(mainPresenter.getNewsByMatchingString("", filter), "", filter)
        }
    }
}
