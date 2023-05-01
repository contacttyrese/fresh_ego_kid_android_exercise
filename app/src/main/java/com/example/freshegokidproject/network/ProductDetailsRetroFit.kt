package com.example.freshegokidproject.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ProductDetailsRetroFit {
    private fun createRetroFit(): Retrofit {
        val client = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(interceptor)
        return Retrofit.Builder()
            .baseUrl(BaseService.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()
    }

    fun createProductDetailsService(): ProductDetailsService {
        return createRetroFit().create(ProductDetailsService::class.java)
    }
}