package com.ddworks.nytimesmostpopular.data.network

import com.ddworks.nytimesmostpopular.data.network.model.NetworkResponseModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

const val API = "https://api.nytimes.com/svc/mostpopular/v2/"
const val API_KEY = "o7sksrGPWxvuRIi8ISHbEmBKi7srjI6u"

interface NYTimesApi {
    @GET("mostviewed/all-sections/7.json")
    fun getData(): Deferred<NetworkResponseModel>
}