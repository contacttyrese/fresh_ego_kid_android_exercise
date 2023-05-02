package com.example.freshegokidproject.network

import com.example.freshegokidproject.model.ProductListPage
import io.reactivex.Observable
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
    fun getProductListPageWithSearchResultsByQuery(@Query("q") query: String): Observable<ProductListPage>
}