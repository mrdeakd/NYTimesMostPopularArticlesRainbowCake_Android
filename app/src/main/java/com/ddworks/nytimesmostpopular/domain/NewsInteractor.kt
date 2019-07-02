package com.ddworks.nytimesmostpopular.domain

import com.ddworks.nytimesmostpopular.disk.NYDiskDataSource
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val nyNetworkDataSource: NetworkDataSource,
    private val nyDiskDataSource: NYDiskDataSource
) {
    suspend fun getUser(): List<DomainNews> {
        val networkNewsList: List<DomainNews>? = nyNetworkDataSource.getData()
        if (networkNewsList != null) {
            nyDiskDataSource.saveNews(networkNewsList)
            return networkNewsList
        }

        return nyDiskDataSource.getNews()
    }
}