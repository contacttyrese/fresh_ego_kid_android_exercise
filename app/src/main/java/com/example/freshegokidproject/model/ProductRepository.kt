package com.example.freshegokidproject.model

import com.example.freshegokidproject.network.HomeService
import com.example.freshegokidproject.network.ProductDetailsService
import com.example.freshegokidproject.network.ProductListService
import io.reactivex.Observable
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val listService: ProductListService,
    private val detailsService: ProductDetailsService,
    private val homeService: HomeService
) {
    fun fetchSearchResultsByQuery(query: String): Observable<ProductListPage> {
        return when (query.isNotBlank()) {
            true -> listService.getProductListPageWithSearchResultsByQuery(query)
            false -> Observable.just(ProductListPage())
        }
    }

    fun fetchDetailsByDetailsUrl(detailsUrl: String): Observable<ProductDetailsPage> {
        return when (detailsUrl.isNotBlank()) {
            true -> detailsService.getProductDetailsPageWithProductDetailsByPath(detailsUrl)
            false -> Observable.just(ProductDetailsPage())
        }
    }

    fun fetchHomeBannerAndProducts(): Observable<HomePage> {
        return homeService.getHomepage()
    }
}