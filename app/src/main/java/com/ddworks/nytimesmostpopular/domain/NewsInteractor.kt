package com.ddworks.nytimesmostpopular.domain

import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val nyNetworkDataSource: NetworkDataSource,
    private val nyDiskDataSource: DiskDataSource
) {
    suspend fun refreshDatabase() {
        val networkNewsList: List<DomainNews> = nyNetworkDataSource.getNews()
        if (networkNewsList.isNotEmpty()) {
            nyDiskDataSource.saveNews(networkNewsList)
        }
    }

    suspend fun getNewNews(): List<DomainNews> = nyDiskDataSource.getNews()

    suspend fun getNewNewsById(newsId : String): DomainNews = nyDiskDataSource.getNewsById(newsId)
}