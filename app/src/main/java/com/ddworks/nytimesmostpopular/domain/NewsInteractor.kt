package com.ddworks.nytimesmostpopular.domain

import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.network.interceptor.ConnectivityInterceptor
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource
import timber.log.Timber
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val nyNetworkDataSource: NetworkDataSource,
    private val nyDiskDataSource: DiskDataSource
) {
    suspend fun getNews(): List<DomainNews> {
        try {
            val networkNewsList: List<DomainNews>? = nyNetworkDataSource.getNews()
            if (networkNewsList != null) {
                nyDiskDataSource.saveNews(networkNewsList)
            }
        } catch (e: ConnectivityInterceptor.NoConnectivityException){
            Timber.d("Nincs internet elérés")
        }
        finally {
            return nyDiskDataSource.getNews()
        }
    }
}