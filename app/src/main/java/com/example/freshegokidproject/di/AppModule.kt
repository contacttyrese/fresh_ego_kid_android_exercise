package com.example.freshegokidproject.di

import android.content.Context
import com.example.freshegokidproject.BaseApplication
import com.example.freshegokidproject.network.ProductDetailsRetroFit
import com.example.freshegokidproject.network.ProductDetailsService
import com.example.freshegokidproject.network.ProductListRetroFit
import com.example.freshegokidproject.network.ProductListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    fun provideListService(): ProductListService {
        return ProductListRetroFit.createProductListService()
    }

    @Provides
    fun provideDetailsService(): ProductDetailsService {
        return ProductDetailsRetroFit.createProductDetailsService()
    }

    @Provides
    fun provideDisposables(): CompositeDisposable {
        return CompositeDisposable()
    }
}