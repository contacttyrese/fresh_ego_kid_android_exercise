package com.example.freshegokidproject.model

import com.example.freshegokidproject.network.HomeService
import io.reactivex.Observable
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService
) {
    fun fetchHomeBannerAndProducts(): Observable<HomePage> {
        return homeService.getHomepage()
    }

}