package com.ddworks.nytimesmostpopular.domain

import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val nyNetworkDataSource: NetworkDataSource,
    private val nyDiskDataSource: DiskDataSource
) {
    suspend fun getNews(): List<DomainNews> {
        //TODO need to fix error handling if there's no internet connection ( this loads data from db now to test it )
        val networkNewsList: List<DomainNews>? = null/*nyNetworkDataSource.getNews()*/
        if (networkNewsList != null) {
            nyDiskDataSource.saveNews(networkNewsList)
            return networkNewsList
        }
        return nyDiskDataSource.getNews()
    }
}