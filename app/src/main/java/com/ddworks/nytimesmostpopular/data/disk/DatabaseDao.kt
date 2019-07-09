package com.ddworks.nytimesmostpopular.data.disk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ddworks.nytimesmostpopular.domain.DomainNews

@Dao
interface DatabaseDao {
    @Query("DELETE FROM newsClass")
    fun deleteAllNews()

    @Query("SELECT * FROM newsClass")
    fun getAllNews(): List<DomainNews>

    @Query("SELECT * FROM newsClass WHERE id=:id")
    fun getNewsById(id: Int): DomainNews

    @Query("SELECT * FROM newsClass WHERE title LIKE '%' || :matchingString || '%' COLLATE NOCASE")
    fun getNewsByMatchingString(matchingString: String): List<DomainNews>

    @Insert
    fun insertNews(art: List<out DomainNews>)
}