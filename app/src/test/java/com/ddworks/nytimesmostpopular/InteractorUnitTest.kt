package com.ddworks.nytimesmostpopular

import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.disk.model.DBNews
import com.ddworks.nytimesmostpopular.data.disk.model.mapToDomainNews
import com.ddworks.nytimesmostpopular.data.network.NetworkDataSource
import com.ddworks.nytimesmostpopular.domain.NewsInteractorImp
import com.ddworks.nytimesmostpopular.domain.mapToDBNews
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InteractorUnitTest {

    companion object {
        private lateinit var diskDS: DiskDataSource
        private lateinit var networkDS: NetworkDataSource
        private lateinit var interactor: NewsInteractorImp
        private const val url = "https://www.nytimes.com/"
        private const val picture = "https://dummyimage.com/600x400/000/fff"
        val listOfNews = listOf(
            DBNews(
                url,
                "By me",
                "Title",
                "2019.01.01",
                1,
                picture
            ),
            DBNews(
                url,
                "By me",
                "Title",
                "2019.01.02",
                2,
                picture
            ),
            DBNews(
                url,
                "By me",
                "Title",
                "2019.01.03",
                3,
                picture
            )
        )
    }

    @Before
    fun setup() {
        diskDS = mockk()
        networkDS = mockk()
        interactor = NewsInteractorImp(networkDS, diskDS)
    }

    @Test
    fun `refreshDatabase fetches data from network and saves it to disk`() = runBlocking {
        every { runBlocking { networkDS.getNews() } } returns listOfNews.map { it.mapToDomainNews() }
        every { diskDS.saveNews(listOfNews) } returns Unit

        interactor.refreshDatabase()

        verify(exactly = 1) {
            runBlocking { networkDS.getNews() }
            diskDS.saveNews(listOfNews)
        }
    }

    @Test
    fun `get news from disk`() {
        every { diskDS.getNews() } returns listOfNews
        val newsOnDisk = interactor.getNewNews()

        assertEquals(listOfNews, newsOnDisk.map { it.mapToDBNews() })
        verify(exactly = 1) {
            diskDS.getNews()
        }
    }

    @Test
    fun `get a news from disk by the given id`() {
        every { diskDS.getNewsById("1") } returns listOfNews[0]
        val n1 = interactor.getNewNewsById("1")

        assertEquals(listOfNews[0], n1.mapToDBNews())
        verify(exactly = 1) {
            diskDS.getNewsById("1")
        }
    }
}
