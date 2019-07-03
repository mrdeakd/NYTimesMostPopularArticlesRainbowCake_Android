package com.ddworks.nytimesmostpopular.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsClass")
data class DomainNews(var url: String,
                      var byline: String,
                      var title: String,
                      var published_date: String,
                      @PrimaryKey var id: Int,
                      var picture: String)