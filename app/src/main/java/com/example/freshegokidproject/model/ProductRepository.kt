package com.example.freshegokidproject.model

import com.example.freshegokidproject.network.ProductDetailsService
import com.example.freshegokidproject.network.ProductListService
import io.reactivex.Observable
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val listService: ProductListService,
    private val detailsService: ProductDetailsService
) {
    fun fetchSearchResultsByQuery(query: String): Observable<ProductListPage> {
        return listService.getPageWithSearchResultsByQuery(query)
    }

    fun fetchDetailsByDetailsUrl(detailsUrl: String): Observable<ProductDetailsPage> {
        return detailsService.getPageWithProductDetailsByPath(detailsUrl)
    }
}