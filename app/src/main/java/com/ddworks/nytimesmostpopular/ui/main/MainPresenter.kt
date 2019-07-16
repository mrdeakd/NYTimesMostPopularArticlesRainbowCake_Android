package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.withIOContext
import com.ddworks.nytimesmostpopular.domain.DomainNews
import com.ddworks.nytimesmostpopular.domain.NewsInteractor
import com.ddworks.nytimesmostpopular.util.Functions

class MainPresenter(
    private val newsInteractor: NewsInteractor
) {
    suspend fun getNews(): List<DomainNews> = withIOContext {
        if(Functions.isConnected()){
            newsInteractor.refreshDatabase()
        }
        newsInteractor.getNewNews()
    }

    suspend fun getNewsByMatchingString(matchingString: String): List<DomainNews> = withIOContext{
        newsInteractor.getNewsByMatchingString(matchingString)
    }
}
