package com.example.freshegokidproject.network

import okhttp3.OkHttpClient
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object ProductListRetroFit {
    private fun createRetroFit(): Retrofit {
        val client = OkHttpClient.Builder()
//            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//            client.addInterceptor(interceptor)
        return Retrofit.Builder()
            .baseUrl(BaseService.API_URL)
            .addConverterFactory(JspoonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()
    }

    fun createProductListService(): ProductListService {
        return createRetroFit().create(ProductListService::class.java)
    }
}