package com.ddworks.nytimesmostpopular.data.disk

import androidx.sqlite.db.SimpleSQLiteQuery
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

    fun getNewsSorted(query: SimpleSQLiteQuery): List<DomainNews> {
        return databaseDao.getNewsSorted(query)
    }
}