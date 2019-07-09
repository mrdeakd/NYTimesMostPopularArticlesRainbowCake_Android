package com.ddworks.nytimesmostpopular.data.disk

import com.ddworks.nytimesmostpopular.domain.DomainNews

class DiskDataSource(
    private val databaseDao: DatabaseDao
) {
    fun saveNews(networkNewsList: List<DomainNews>) {
        databaseDao.deleteAllNews()
        databaseDao.insertNews(networkNewsList)
    }

    fun getNews(): List<DomainNews> {
        return databaseDao.getAllNews()
    }

    fun getNewsById(id : String): DomainNews {
        return databaseDao.getNewsById(id.toInt())
    }

    fun getNewsByMatchingString(matchingString : String): List<DomainNews> {
        return databaseDao.getNewsByMatchingString(matchingString)
    }
}