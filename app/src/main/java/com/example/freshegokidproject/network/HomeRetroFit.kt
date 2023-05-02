package com.example.freshegokidproject.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object HomeRetroFit {
    private fun createRetroFit(): Retrofit {
//        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        client.addInterceptor(interceptor)
        return Retrofit.Builder()
            .baseUrl(BaseService.API_URL)
            .addConverterFactory(JspoonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()
    }

    fun createHomeService(): HomeService {
        return createRetroFit().create(HomeService::class.java)
    }
}