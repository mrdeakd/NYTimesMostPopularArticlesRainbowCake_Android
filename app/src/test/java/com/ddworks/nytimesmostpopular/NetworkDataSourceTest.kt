package com.ddworks.nytimesmostpopular

import com.ddworks.nytimesmostpopular.data.network.NYTimesApi
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource
import com.ddworks.nytimesmostpopular.data.network.model.NetworkArticleModel
import com.ddworks.nytimesmostpopular.data.network.model.NetworkMediaMetaModel
import com.ddworks.nytimesmostpopular.data.network.model.NetworkMediaModel
import com.ddworks.nytimesmostpopular.data.network.model.NetworkResponseModel
import com.ddworks.nytimesmostpopular.domain.DomainNews
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NetworkDataSourceTest {

    companion object{
        private lateinit var nytAPI: NYTimesApi
        private lateinit var networkDS: NetworkDataSource
        private val news1 = DomainNews("", "", "", "", 10, "url1")
        private val news2 = DomainNews("", "", "", "", 11, "url2")
        private val listOfNews = listOf(news1, news2)
    }

    private val apiResponse = NetworkResponseModel(listOf(
        NetworkArticleModel("","","","",10,listOf(
            NetworkMediaModel(
                listOf(NetworkMediaMetaModel("url1",""))
            )
        )),
        NetworkArticleModel("","","","",11,listOf(
            NetworkMediaModel(
                listOf(NetworkMediaMetaModel("url2",""))
            )
        ))
    ))

    @Before
    fun setup() {
        nytAPI = mockk()
        networkDS = NetworkDataSource(nytAPI)
    }

    @Test
    fun `get all news from the network`() = runBlocking {
        every { runBlocking { nytAPI.getData().await() } } returns apiResponse

        val news = networkDS.getNews()

        Assert.assertEquals(listOfNews, news)
        verify {
            nytAPI.getData()
        }
    }

}

