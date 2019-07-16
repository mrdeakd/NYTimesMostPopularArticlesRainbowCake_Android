package com.ddworks.nytimesmostpopular.data.disk

import androidx.sqlite.db.SimpleSQLiteQuery
import com.ddworks.nytimesmostpopular.util.SortOptions

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

    fun getNewsSorted(matchingString: String, filter: String): List<DBNews> {
        var query = "SELECT * FROM newsClass"
        if(!matchingString.equals("")){
            query = "$query WHERE title LIKE '%' || '$matchingString' || '%' COLLATE NOCASE"
        }
        when(filter){
            SortOptions.SORT_BY_DATE -> query = "$query ORDER BY published_date ASC"
            SortOptions.SORT_BY_ABC -> query = "$query ORDER BY title ASC"
        }
        return databaseDao.getNewsSorted(SimpleSQLiteQuery(query))
    }
}