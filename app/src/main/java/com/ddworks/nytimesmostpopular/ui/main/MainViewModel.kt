package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.base.JobViewModel
import com.ddworks.nytimesmostpopular.util.Functions

class MainViewModel(
    private val mainPresenter: MainPresenter
) : JobViewModel<MainViewState>(Loading) {
    fun loadAll() = execute {
        val previousState = viewState
        viewState = Loading
        viewState = when(previousState){
            is NewsSearching -> NewsSearching(mainPresenter.getUser(),previousState.searchString)
            else -> NewsLoaded(mainPresenter.getUser())
        }
    }

    fun checkInternetConnection(): Boolean{
        if(!Functions.isConnected()){
            viewState = NoConnection
            return false
        }
        return true
    }

    fun openDetail(newsId: Int) {
        viewState = NewsDetailedLoaded(newsId)
    }

    fun setStateToLoaded() {
        viewState = Loaded
    }

    fun changeSearchString(searchString: String){
        when(viewState){
            is NewsSearching -> viewState = (viewState as NewsSearching).copy(
                searchString = searchString
            )
            is NewsLoaded -> viewState = NewsSearching((viewState as NewsLoaded).dataList, searchString)
        }
    }
}
