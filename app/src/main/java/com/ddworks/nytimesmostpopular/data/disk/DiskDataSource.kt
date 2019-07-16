package com.ddworks.nytimesmostpopular.data.disk

import androidx.sqlite.db.SimpleSQLiteQuery
import com.ddworks.nytimesmostpopular.domain.DBNews

class DiskDataSource(
    private val databaseDao: DatabaseDao
) {
    fun saveNews(networkNewsList: List<DBNews>) {
        databaseDao.deleteAllNews()
        databaseDao.insertNews(networkNewsList)
    }

    fun getNews(): List<DBNews> {
        return databaseDao.getAllNews()
    }

    fun getNewsById(id : String): DBNews {
        return databaseDao.getNewsById(id.toInt())
    }

    fun getNewsSorted(query: SimpleSQLiteQuery): List<DBNews> {
        return databaseDao.getNewsSorted(query)
    }
}