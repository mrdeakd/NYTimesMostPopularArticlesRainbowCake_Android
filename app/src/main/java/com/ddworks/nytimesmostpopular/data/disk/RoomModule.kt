package com.ddworks.nytimesmostpopular.data.disk

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoom(context: Context): NewsRoomDatabase{
        return NewsRoomDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDatabaseDao(newsRoomDatabase: NewsRoomDatabase): DatabaseDao{
        return newsRoomDatabase.newsDataDao()
    }

}