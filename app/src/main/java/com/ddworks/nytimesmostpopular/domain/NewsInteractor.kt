package com.ddworks.nytimesmostpopular.domain

import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource


class NewsInteractor(
    private val nyNetworkDataSource: NetworkDataSource,
    private val nyDiskDataSource: DiskDataSource
) {
    suspend fun refreshDatabase() {
        val networkNewsList: List<DomainNews> = nyNetworkDataSource.getNews()
        if (networkNewsList.isNotEmpty()) {
            nyDiskDataSource.saveNews(networkNewsList)
        }
    }

    fun getNewNews(): List<DomainNews> = nyDiskDataSource.getNews()

    fun getNewNewsById(newsId : String): DomainNews = nyDiskDataSource.getNewsById(newsId)

    fun getNewsByMatchingString(matchingString : String): List<DomainNews> = nyDiskDataSource.getNewsByMatchingString(matchingString)
}