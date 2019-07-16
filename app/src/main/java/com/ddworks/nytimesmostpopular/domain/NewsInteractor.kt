package com.ddworks.nytimesmostpopular.domain

import androidx.sqlite.db.SimpleSQLiteQuery

interface NewsInteractor {
    suspend fun refreshDatabase()

    fun getNewNews(): List<DomainNews>

    fun getNewNewsById(newsId : String): DomainNews

    fun getNewNewsSorted(query: SimpleSQLiteQuery): List<DomainNews>
}