package com.example.freshegokidproject.model

import io.reactivex.Observable
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val service: ProductListService
) {
    fun fetchSearchResultsByQuery(query: String): Observable<ProductListPage> {
        return service.getPageWithSearchResultsByQuery(query)
    }
}