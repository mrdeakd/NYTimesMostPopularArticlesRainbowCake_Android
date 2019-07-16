package com.ddworks.nytimesmostpopular.ui.main

import androidx.sqlite.db.SimpleSQLiteQuery
import co.zsmb.rainbowcake.withIOContext
import com.ddworks.nytimesmostpopular.domain.DomainNews
import com.ddworks.nytimesmostpopular.domain.NewsInteractor
import com.ddworks.nytimesmostpopular.util.Functions
import com.ddworks.nytimesmostpopular.util.SortOptions

class MainPresenter(
    private val newsInteractor: NewsInteractor
) {
    suspend fun getNews(): List<DomainNews> = withIOContext {
        if(Functions.isConnected()){
            newsInteractor.refreshDatabase()
        }
        newsInteractor.getNewNews()
    }

    suspend fun getNewsByMatchingString(matchingString: String, filter: String): List<DomainNews> = withIOContext{
        var query = "SELECT * FROM newsClass"
        if(!matchingString.equals("")){
            query = "$query WHERE title LIKE '%' || '$matchingString' || '%' COLLATE NOCASE"
        }
        when(filter){
            SortOptions.SORT_BY_DATE -> query = "$query ORDER BY published_date ASC"
            SortOptions.SORT_BY_ABC -> query = "$query ORDER BY title ASC"
        }
        newsInteractor.getNewNewsSorted(SimpleSQLiteQuery(query))
    }
}
