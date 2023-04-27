package com.example.freshegokidproject.viewmodel

import com.example.freshegokidproject.model.ProductListPage

sealed class SearchViewState {
    object Loading: SearchViewState()
//    data class ProductLoaded(val searchResults: List<ProductSearchResult>): SearchViewState()
    data class ProductLoaded(val searchResults: ProductListPage): SearchViewState()
    data class ProductLoadError(val throwable: Throwable): SearchViewState()
}