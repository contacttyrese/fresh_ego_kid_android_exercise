package com.example.freshegokidproject.model

import com.example.freshegokidproject.helpers.RepositoryHelper
import com.example.freshegokidproject.network.ProductListService
import io.reactivex.Observable
import javax.inject.Inject

class ListRepository @Inject constructor(
    private val listService: ProductListService,
    private val helper: RepositoryHelper
) {

    fun fetchSearchResultsByQuery(query: String): Observable<ProductListPage> {
        return when (helper.checkQueryIsValid(query)) {
            true -> listService.getProductListPageWithSearchResultsByQuery(query)
            false -> Observable.just(ProductListPage())
        }
    }
}