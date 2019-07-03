package com.ddworks.nytimesmostpopular.data.disk

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ddworks.nytimesmostpopular.domain.DomainNews

@Database(entities = [DomainNews::class], version = 1, exportSchema = false)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun newsDataDao(): DatabaseDao
}