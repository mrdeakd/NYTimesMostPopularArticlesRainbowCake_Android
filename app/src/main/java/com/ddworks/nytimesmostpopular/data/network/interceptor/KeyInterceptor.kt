package com.ddworks.nytimesmostpopular.data.network.interceptor

import com.ddworks.nytimesmostpopular.data.network.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("api-key",
            API_KEY
        ).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}