package com.ddworks.nytimesmostpopular.data.disk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery

@Dao
interface DatabaseDao {
    @Query("DELETE FROM newsClass")
    fun deleteAllNews()

    @Query("SELECT * FROM newsClass")
    fun getAllNews(): List<DBNews>

    @Query("SELECT * FROM newsClass WHERE id=:id")
    fun getNewsById(id: Int): DBNews

    @RawQuery
    fun getNewsSorted(query: SimpleSQLiteQuery): List<DBNews>

    @Insert
    fun insertNews(art: List<DBNews>)
}