package com.example.freshegokidproject.model

import android.os.Build
import android.text.Html
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsInteractor @Inject constructor(
    private val repository: DetailsRepository
) {
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