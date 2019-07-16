package com.ddworks.nytimesmostpopular.domain

import com.ddworks.nytimesmostpopular.data.disk.model.DBNews

data class DomainNews(
    var url: String,
    var byline: String,
    var title: String,
    var published_date: String,
    var id: Int,
    var picture: String
)

fun DomainNews.mapToDBNews(): DBNews =
    DBNews(
        id = this.id,
        url = this.url,
        byline = this.byline,
        title = this.title,
        published_date = this.published_date,
        picture = this.picture
    )