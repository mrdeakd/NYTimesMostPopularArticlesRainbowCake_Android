package com.ddworks.nytimesmostpopular.data.disk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import com.ddworks.nytimesmostpopular.domain.DomainNews

@Dao
interface DatabaseDao {
    @Query("DELETE FROM newsClass")
    fun deleteAllNews()

    @Query("SELECT * FROM newsClass")
    fun getAllNews(): List<DomainNews>

    @Query("SELECT * FROM newsClass WHERE id=:id")
    fun getNewsById(id: Int): DomainNews

    @RawQuery
    fun getNewsSorted(query: SimpleSQLiteQuery): List<DomainNews>

    @Insert
    fun insertNews(art: List<DomainNews>)
}