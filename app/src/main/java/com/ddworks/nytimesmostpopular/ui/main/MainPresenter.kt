package com.ddworks.nytimesmostpopular.ui.main

import co.zsmb.rainbowcake.withIOContext
import com.ddworks.nytimesmostpopular.domain.NewsInteractor
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val newsInteractor: NewsInteractor
) {
    suspend fun getUser(): List<News> = withIOContext {
        newsInteractor.getNews().map {
            News(
                name = it.title,
                profileImage = it.picture
            )
        }
    }

    // Presentation model
    class News(name: String, profileImage: String)
}
