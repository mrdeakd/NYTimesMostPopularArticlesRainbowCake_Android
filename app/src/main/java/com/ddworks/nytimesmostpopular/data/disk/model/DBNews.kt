package com.ddworks.nytimesmostpopular.data.disk.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ddworks.nytimesmostpopular.domain.DomainNews

@Entity(tableName = "newsClass")
data class DBNews(
    var url: String,
    var byline: String,
    var title: String,
    var published_date: String,
    @PrimaryKey var id: Int,
    var picture: String
)

fun DBNews.mapToDomainNews(): DomainNews =
    DomainNews(
        id = this.id,
        url = this.url,
        byline = this.byline,
        title = this.title,
        published_date = this.published_date,
        picture = this.picture
    )