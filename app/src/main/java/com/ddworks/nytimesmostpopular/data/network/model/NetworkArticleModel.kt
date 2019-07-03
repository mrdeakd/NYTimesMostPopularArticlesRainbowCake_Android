package com.ddworks.nytimesmostpopular.data.network.model

data class NetworkArticleModel(
    var url: String,
    var byline: String,
    var title: String,
    var published_date: String,
    var id: Number,
    val media: List<NetworkMediaModel>
)