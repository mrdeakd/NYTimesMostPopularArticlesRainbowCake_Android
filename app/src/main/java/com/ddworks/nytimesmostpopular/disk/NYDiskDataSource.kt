package com.ddworks.nytimesmostpopular.disk

import com.ddworks.nytimesmostpopular.domain.DomainNews
import javax.inject.Inject

class NYDiskDataSource @Inject constructor() {
    fun saveNews(networkNewsList : List<DomainNews>){}
    fun getNews(): List<DomainNews> {
        return emptyList()
    }
}