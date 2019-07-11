package com.ddworks.nytimesmostpopular.domain

interface NewsInteractor {
    suspend fun refreshDatabase()

    fun getNewNews(): List<DomainNews>

    fun getNewNewsById(newsId : String): DomainNews

    fun getNewsByMatchingString(matchingString : String): List<DomainNews>
}