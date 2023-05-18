package com.example.freshegokidproject.model

import android.util.Log
import com.example.freshegokidproject.helpers.InteractorHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val repository: HomeRepository,
    private val helper: InteractorHelper
) {

    fun getPageObservableWithHomeBannerAndHomeProducts(): Observable<HomePage> {
        return repository.fetchHomeBannerAndProducts()
            .subscribeOn(Schedulers.io())
            .doOnNext { page ->
                page.products.forEach { searchResult ->
                    helper.refactorImageUrlsForPage(searchResult)
                }

                page.bannerUrl?.let { bannerUrl ->
                    page.bannerUrl = "https:$bannerUrl"
                    Log.i("banner_url_refactored", "image refactored url: ${page.bannerUrl}")
                } ?: kotlin.run {
                    Log.e("next_bannerurl_error", "banner url is null")
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

}