package com.example.freshegokidproject.viewmodel

import com.example.freshegokidproject.model.SearchResult

sealed class MainViewState {
    object LoadingBannerAndProducts : MainViewState()
    data class LoadingError(val throwable: Throwable) : MainViewState()
    data class LoadingSuccess(val bannerUrl: String, val products: List<SearchResult>) : MainViewState()
}