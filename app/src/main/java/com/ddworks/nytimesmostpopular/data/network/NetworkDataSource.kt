package com.ddworks.nytimesmostpopular.data.network

import com.ddworks.nytimesmostpopular.data.network.model.NetworkArticleModel
import com.ddworks.nytimesmostpopular.domain.DomainNews
import com.ddworks.nytimesmostpopular.util.toDomainNews
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(
    private val NYTimesAPI: NYTimesApi
) {
    suspend fun getNews(): List<DomainNews> {
        return NYTimesAPI.getData().await().results.map(NetworkArticleModel::toDomainNews)
    }
}