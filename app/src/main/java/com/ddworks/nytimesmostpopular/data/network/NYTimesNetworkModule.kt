package com.ddworks.nytimesmostpopular.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NYTimesNetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val keyInterceptor = Interceptor{
            var request = it.request()
            val url = request.url().newBuilder().addQueryParameter("api-key", API_KEY).build()
            request = request.newBuilder().url(url).build()
            return@Interceptor it.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(keyInterceptor)
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