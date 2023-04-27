package com.example.freshegokidproject.model

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductInteractor @Inject constructor(
    private val repository: ProductRepository
) {
    fun getPageObservableWithSearchResultsByQuery(query: String): Observable<ProductListPage> {
        return repository.fetchSearchResultsByQuery(query)
            .subscribeOn(Schedulers.io())
            .doOnNext { page ->
                when (page.productSearchResults.isNotEmpty()) {
                    true -> {
                        page.productSearchResults.forEach { searchResult ->
                            searchResult.imageUrl?.let { imageUrl ->
                                val refactoredUrl = imageUrl.replace("{width}", "360")
                                Log.i("image_url_raw", "print image raw url: $imageUrl")
                                searchResult.imageUrl = "https:$refactoredUrl"
                            } ?: kotlin.run {
                                Log.e("on_next_imageurl_error", "image url for search result is null")
                            }

                            searchResult.detailsUrl?.let { detailsUrl ->
                                val host = ProductListService.API_URL.subSequence(0, ProductListService.API_URL.length-1)
                                Log.i("details_url_raw", "print details raw url: $detailsUrl")

//                        detailsUrl.split("products/","?", ignoreCase =  true, limit =  0).forEach {
//                            Log.i("test", "test string for key: $it")
//                        }
                                val key = detailsUrl.split("products/","?", ignoreCase =  true, limit =  0)[1]
                                Log.i("search_result_key", "product key $key")
                            }
                        }
                    }
                    false -> {
                        Log.e("search_results_check", "search results were empty")
                    }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

//    fun getProductsObservable(): Observable<List<ProductSearchResult>> {
//        return repository.fetchProductsByKeyword("hat")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    }
}