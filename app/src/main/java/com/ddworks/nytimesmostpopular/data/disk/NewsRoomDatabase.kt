package com.ddworks.nytimesmostpopular.data.disk

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ddworks.nytimesmostpopular.domain.DomainNews

@Database(entities = [DomainNews::class], version = 1, exportSchema = false)
abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun newsDataDao(): DatabaseDao

    companion object {
        private var INSTANCE: NewsRoomDatabase? = null

        fun getInstance(context: Context): NewsRoomDatabase {

            if (INSTANCE == null) {
                synchronized(NewsRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder(context.applicationContext, NewsRoomDatabase::class.java, "news_db")
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}