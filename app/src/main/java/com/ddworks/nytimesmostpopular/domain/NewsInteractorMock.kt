package com.ddworks.nytimesmostpopular.domain

class NewsInteractorMock : NewsInteractor {

    val list = listOf(
        DomainNews("https://www.nytimes.com/","By me", "Title","2019.01.01",1,"https://dummyimage.com/600x400/000/fff"),
        DomainNews("https://www.nytimes.com/","By me", "Title","2019.01.02",2,"https://dummyimage.com/600x400/000/fff"),
        DomainNews("https://www.nytimes.com/","By me", "Title","2019.01.03",3,"https://dummyimage.com/600x400/000/fff")
    )

    override suspend fun refreshDatabase() {}

    override fun getNewNews(): List<DomainNews> = list

    override fun getNewNewsById(newsId : String): DomainNews = list.filter { it.id.toString() == newsId }[0]

    override fun getNewsByMatchingString(matchingString : String): List<DomainNews> = list.filter { it.title.contains(matchingString)}
}