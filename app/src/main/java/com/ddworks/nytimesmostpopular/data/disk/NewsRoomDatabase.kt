package com.ddworks.nytimesmostpopular.data.disk

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBNews::class], version = 2, exportSchema = false)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun newsDataDao(): DatabaseDao
}