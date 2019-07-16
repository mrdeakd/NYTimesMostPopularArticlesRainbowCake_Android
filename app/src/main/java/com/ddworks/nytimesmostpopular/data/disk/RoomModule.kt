package com.ddworks.nytimesmostpopular.data.disk

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DatabaseModule = module {
    single { provideRoom(androidContext()) }
    single { provideDatabaseDao(get()) }
    single { DiskDataSource(get()) }
}

fun provideRoom(context: Context): NewsRoomDatabase {
    return Room.databaseBuilder(context.applicationContext, NewsRoomDatabase::class.java, "news_db")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideDatabaseDao(newsRoomDatabase: NewsRoomDatabase): DatabaseDao {
    return newsRoomDatabase.newsDataDao()
}
