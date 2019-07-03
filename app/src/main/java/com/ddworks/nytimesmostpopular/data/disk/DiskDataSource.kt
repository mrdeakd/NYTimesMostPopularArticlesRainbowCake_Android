package com.ddworks.nytimesmostpopular.data.disk

import com.ddworks.nytimesmostpopular.domain.DomainNews
import javax.inject.Inject

class DiskDataSource @Inject constructor() {
    fun saveNews(networkNewsList : List<DomainNews>){}
    fun getNews(): List<DomainNews> {
        return emptyList()
    }
}