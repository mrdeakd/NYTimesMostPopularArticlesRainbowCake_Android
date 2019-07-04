package com.ddworks.nytimesmostpopular.data.disk

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoom(context: Context): NewsRoomDatabase {
        return Room.databaseBuilder(context.applicationContext, NewsRoomDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseDao(newsRoomDatabase: NewsRoomDatabase): DatabaseDao {
        return newsRoomDatabase.newsDataDao()
    }

}