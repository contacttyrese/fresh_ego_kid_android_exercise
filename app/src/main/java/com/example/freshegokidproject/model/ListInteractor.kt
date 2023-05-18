package com.example.freshegokidproject.model

import android.util.Log
import com.example.freshegokidproject.helpers.InteractorHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListInteractor @Inject constructor(
    private val repository: ListRepository,
    private val helper: InteractorHelper
) {
    fun getPageObservableWithSearchResultsByQuery(query: String): Observable<ProductListPage> {
        return repository.fetchSearchResultsByQuery(query)
            .subscribeOn(Schedulers.io())
            .doOnNext { page ->
                when (page.searchResults.isNotEmpty()) {
                    true -> {
                        page.searchResults.forEach { searchResult ->
                            helper.refactorImageUrlsForPage(searchResult)

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
}