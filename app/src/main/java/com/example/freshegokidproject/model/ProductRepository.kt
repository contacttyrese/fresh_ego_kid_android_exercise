package com.example.freshegokidproject.model

import android.util.Log
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
        return when (checkQueryIsValid(query)) {
            true -> listService.getProductListPageWithSearchResultsByQuery(query)
            false -> Observable.just(ProductListPage())
        }
    }

    fun fetchDetailsByDetailsUrl(detailsUrl: String): Observable<ProductDetailsPage> {
        return when (detailsUrl.isNotBlank()) {
            true -> {
                Log.i("repo_url_valid", "details url is valid")
                detailsService.getProductDetailsPageWithProductDetailsByPath(detailsUrl)
            }
            false -> {
                Log.e("repo_url_invalid", "details url is blank returning empty details")
                Observable.just(ProductDetailsPage())
            }
        }
    }

    fun fetchHomeBannerAndProducts(): Observable<HomePage> {
        return homeService.getHomepage()
    }

    private fun checkQueryIsValid(query: String): Boolean {
        val isQueryValid = query.isNotBlank() && query.all { char ->
            !char.isDigit() || char.isLetter() || char.isWhitespace()
        }
        when (isQueryValid) {
            true -> Log.i("repo_query_valid", "query is valid, making network call")
            false -> Log.e("repo_query_invalid", "query is invalid, returning empty search results")
        }
        return isQueryValid
    }
}