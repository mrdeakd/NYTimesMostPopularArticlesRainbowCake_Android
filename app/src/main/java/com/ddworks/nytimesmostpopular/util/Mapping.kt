package com.ddworks.nytimesmostpopular.util

import com.ddworks.nytimesmostpopular.data.network.model.NetworkArticleModel
import com.ddworks.nytimesmostpopular.domain.DomainNews

fun NetworkArticleModel.toDomainNews() = DomainNews(
    url = this.url,
    byline = this.byline,
    title = this.title,
    published_date = this.published_date,
    id = this.id.toInt(),
    picture = this.media[0].media_metadata[0].url
)