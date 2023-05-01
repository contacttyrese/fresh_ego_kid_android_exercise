package com.example.freshegokidproject.viewmodel

import com.example.freshegokidproject.model.ProductListPage

sealed class SearchViewState {
    object Loading: SearchViewState()
    data class ProductLoaded(val page: ProductListPage): SearchViewState()
    data class ProductLoadError(val throwable: Throwable): SearchViewState()
}