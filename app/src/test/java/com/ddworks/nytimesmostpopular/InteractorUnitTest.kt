package com.ddworks.nytimesmostpopular

import com.ddworks.nytimesmostpopular.data.disk.DiskDataSource
import com.ddworks.nytimesmostpopular.data.disk.model.DBNews
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
        private val news1 = DBNews("", "", "", "", 10, "")
        private val news2 = DBNews("", "", "", "", 11, "")
        private val listOfNews = listOf(news1, news2)
    }

    @Before
    fun setup() {
        diskDS = mockk()
        networkDS = mockk()
        interactor = NewsInteractorImp(networkDS, diskDS)
    }

    @Test
    fun `refreshDatabase fetches data from network and saves it to disk`() = runBlocking {
        every { runBlocking { networkDS.getNews().map { it.mapToDBNews() } } } returns listOfNews
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

        assertEquals(listOfNews, newsOnDisk)
        verify(exactly = 1) {
            diskDS.getNews()
        }
    }

    @Test
    fun `get a news from disk by the given id`() {
        every { diskDS.getNewsById("10") } returns news1
        val n1 = interactor.getNewNewsById("10")

        assertEquals(news1, n1)
        verify(exactly = 1) {
            diskDS.getNewsById("10")
        }
    }
}
