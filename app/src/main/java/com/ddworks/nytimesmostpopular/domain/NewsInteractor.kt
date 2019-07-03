package com.ddworks.nytimesmostpopular.domain

import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val nyNetworkDataSource: NetworkDataSource,
    private val nyDiskDataSource: DiskDataSource
) {
    suspend fun getUser(): List<DomainNews> {
        val networkNewsList: List<DomainNews>? = nyNetworkDataSource.getNews()
        if (networkNewsList != null) {
            nyDiskDataSource.saveNews(networkNewsList)
            return networkNewsList
        }

        return nyDiskDataSource.getNews()
    }
}