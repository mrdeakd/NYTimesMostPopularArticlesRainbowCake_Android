package com.ddworks.nytimesmostpopular.domain

import androidx.sqlite.db.SimpleSQLiteQuery
import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource


class NewsInteractorImp(
    private val nyNetworkDataSource: NetworkDataSource,
    private val nyDiskDataSource: DiskDataSource
) : NewsInteractor {


    override suspend fun refreshDatabase() {
        val networkNewsList: List<DomainNews> = nyNetworkDataSource.getNews()
        if (networkNewsList.isNotEmpty()) {
            nyDiskDataSource.saveNews(networkNewsList)
        }
    }

    override fun getNewNews(): List<DomainNews> = nyDiskDataSource.getNews()

    override fun getNewNewsById(newsId : String): DomainNews = nyDiskDataSource.getNewsById(newsId)

    override fun getNewNewsSorted(query: SimpleSQLiteQuery): List<DomainNews> = nyDiskDataSource.getNewsSorted(query)
}