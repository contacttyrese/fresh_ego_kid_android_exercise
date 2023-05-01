package com.example.freshegokidproject.model

import android.os.Build
import android.text.Html
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
                when (page.searchResults.isNotEmpty()) {
                    true -> {
                        page.searchResults.forEach { searchResult ->
                            searchResult.imageUrl?.let { imageUrl ->
                                val refactoredUrl = imageUrl.replace("{width}", "360")
                                Log.i("image_url_raw", "print image raw url: $imageUrl")
                                searchResult.imageUrl = "https:$refactoredUrl"
                                Log.i("image_url_refactored", "image refactored url: ${searchResult.imageUrl}")
                            } ?: kotlin.run {
                                Log.e("next_imageurl_error", "image url for search result is null")
                            }

                            searchResult.detailsUrl?.let { detailsUrl ->
                                searchResult.key = detailsUrl.split("products/","?", ignoreCase =  true, limit =  0)[1]
                                searchResult.detailsUrl = detailsUrl.split("?")[0]
                                Log.i("details_url_raw", "print details raw url: $detailsUrl")
                            } ?: kotlin.run {
                                Log.e("next_detailsurl_error", "details url for search result is null")
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

    fun getPageObservableWithDetailsByDetailsUrl(detailsUrl: String): Observable<ProductDetailsPage> {
        return repository.fetchDetailsByDetailsUrl(detailsUrl)
            .subscribeOn(Schedulers.io())
            .doOnNext { page ->
                page.details.description
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    page.details.description = Html.fromHtml(page.details.description, Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM).toString()
                } else {
                    Html.fromHtml(page.details.description)
                }

            }
            .observeOn(AndroidSchedulers.mainThread())
    }

}