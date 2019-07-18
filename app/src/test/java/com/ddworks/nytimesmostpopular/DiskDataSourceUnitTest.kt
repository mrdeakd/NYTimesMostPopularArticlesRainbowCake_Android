package com.ddworks.nytimesmostpopular

import com.ddworks.nytimesmostpopular.data.disk.DatabaseDao
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DiskDataSourceUnitTest {

    companion object {
        private lateinit var dao: DatabaseDao
        private lateinit var diskDS: DiskDataSource
        private val news1 = DBNews("", "", "", "", 10, "")
        private val news2 = DBNews("", "", "", "", 11, "")
        private val listOfNews = listOf(news1, news2)
    }

    @Before
    fun setup() {
        dao = mockk()
        diskDS = DiskDataSource(dao)
    }

    @Test
    fun `save news to disk and verify the functions that it calls`() {
        every { dao.deleteAllNews() } returns Unit
        every { dao.insertNews(listOfNews) } returns Unit

        diskDS.saveNews(listOfNews)

        verify {
            dao.deleteAllNews()
            dao.insertNews(listOfNews)
        }
    }

    @Test
    fun `get all news from disk`() {
        every { dao.getAllNews() } returns listOfNews

        val newsOnDisk = diskDS.getNews()

        Assert.assertEquals(listOfNews, newsOnDisk)

        verify {
            dao.getAllNews()
        }
    }

    @Test
    fun `get a news with the give id`() {
        every { dao.getNewsById(10) } returns news1

        val n1 = diskDS.getNewsById("10")

        Assert.assertEquals(news1, n1)

        verify {
            dao.getNewsById(10)
        }
    }

    @Test
    fun `interactor refreshes the database from the network`() {
        val dao = mockk<DatabaseDao>()
        val networkDS = mockk<NetworkDataSource>()
        val diskDS = DiskDataSource(dao)
        val interactor = NewsInteractorImp(networkDS, diskDS)

        val news = listOf(
            DBNews("", "", "", "", 10, ""),
            DBNews("", "", "", "", 11, "")
        )

        every { runBlocking { networkDS.getNews() } } returns news.map { it.mapToDomainNews() }
        every { dao.getAllNews() } returns news
        every { dao.deleteAllNews() } returns Unit
        every { dao.insertNews(news) } returns Unit

        runBlocking {
            interactor.refreshDatabase()
        }

        val list = interactor.getNewNews().map { it.mapToDBNews() }

        Assert.assertEquals(list, news)
        verify {
            dao.deleteAllNews()
            dao.insertNews(news)
        }
    }
}