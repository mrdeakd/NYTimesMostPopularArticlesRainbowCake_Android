package com.ddworks.nytimesmostpopular.data.network

import android.content.Context
import com.ddworks.nytimesmostpopular.data.network.interceptor.ConnectivityInterceptor
import com.ddworks.nytimesmostpopular.data.network.interceptor.KeyInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NYTimesNetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(context: Context): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor(context))
            .addInterceptor(KeyInterceptor())
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(API)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): NYTimesApi = retrofit.create(NYTimesApi::class.java)
}