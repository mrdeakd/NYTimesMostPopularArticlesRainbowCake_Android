package com.ddworks.nytimesmostpopular.domain

import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.disk.mapToDomainNews
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource

class NewsInteractorImp(
    private val nyNetworkDataSource: NetworkDataSource,
    private val nyDiskDataSource: DiskDataSource
) : NewsInteractor {

    override suspend fun refreshDatabase() {
        val networkNewsList: List<DomainNews> = nyNetworkDataSource.getNews()
        if (networkNewsList.isNotEmpty()) {
            nyDiskDataSource.saveNews(networkNewsList.map { it.mapToDBNews() })
        }
    }

    override fun getNewNews(): List<DomainNews> = nyDiskDataSource.getNews().map { it.mapToDomainNews() }

    override fun getNewNewsById(newsId: String): DomainNews = nyDiskDataSource.getNewsById(newsId).mapToDomainNews()

    override fun getNewNewsSorted(matchingString: String, filter: String): List<DomainNews> =
        nyDiskDataSource.getNewsSorted(matchingString, filter).map { it.mapToDomainNews() }
}