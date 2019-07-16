package com.ddworks.nytimesmostpopular.ui.details

import co.zsmb.rainbowcake.withIOContext
import com.ddworks.nytimesmostpopular.domain.DomainNews
import com.ddworks.nytimesmostpopular.domain.NewsInteractor

class DetailsPresenter(
    private val newsInteractor: NewsInteractor
) {

    suspend fun getDataById(newsId: String): DomainNews = withIOContext {
        newsInteractor.getNewNewsById(newsId)
    }
}
