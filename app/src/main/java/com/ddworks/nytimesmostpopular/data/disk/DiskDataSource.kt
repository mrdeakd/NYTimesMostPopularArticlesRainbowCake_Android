package com.ddworks.nytimesmostpopular.data.disk

import com.ddworks.nytimesmostpopular.domain.DomainNews
import javax.inject.Inject

class DiskDataSource @Inject constructor(
    private val databaseDao: DatabaseDao
) {
    suspend fun saveNews(networkNewsList : List<DomainNews>){
        databaseDao.deleteAllNews()
        databaseDao.insertNews(networkNewsList)
    }
    suspend fun getNews(): List<DomainNews> {
        return databaseDao.getAllNews()
    }
}