package com.example.freshegokidproject.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductListService {
    @Headers(
        "Host: www.freshegokid.com",
        "Connection: www.freshegokid.com",
        "Upgrade-Insecure-Requests: 1",
        "User-Agent: com.freshegoproject/0.0.1 Dalvik/2.1.0 (Linux; Android 11; moto g(8) power)",
        "Accept: application/json",
        "Accept-Language: en-US,en;q=0.9"
    )
    @GET("/search?type=product")
    fun getProductsByQuery(@Query("q") query: String): Single<ProductSearchResult>

    companion object {
        const val API_URL = "https://www.freshegokid.com/"
    }

}