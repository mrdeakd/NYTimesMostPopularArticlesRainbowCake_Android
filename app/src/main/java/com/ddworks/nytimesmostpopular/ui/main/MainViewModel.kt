package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.base.JobViewModel
import com.ddworks.nytimesmostpopular.util.Functions

class MainViewModel(
    private val mainPresenter: MainPresenter
) : JobViewModel<MainViewState>(Loading) {
    fun loadAll() = execute {

        viewState = Loading
        viewState = NewsLoaded(mainPresenter.getUser(),"")
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
        if(viewState is NewsLoaded)
            viewState = (viewState as NewsLoaded).copy(searchString = searchString)
    }
}
