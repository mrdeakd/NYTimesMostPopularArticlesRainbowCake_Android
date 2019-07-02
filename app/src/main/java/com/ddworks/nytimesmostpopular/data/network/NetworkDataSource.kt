package com.ddworks.nytimesmostpopular.data.network

import com.ddworks.nytimesmostpopular.domain.DomainNews
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(
    private val NYTimesAPI: NYTimesApi
) {
    suspend fun getData(): List<DomainNews> {
        return NYTimesAPI.getData().await().results.map {
            DomainNews(
                url = it.url,
                byline = it.byline,
                title = it.title,
                published_date = it.published_date,
                id = it.id.toInt(),
                picture = it.media[0].media_metadata[0].url
            )
        }
        //...error handling
    }
}