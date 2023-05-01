package com.example.freshegokidproject.viewmodel

import com.example.freshegokidproject.model.ProductDetailsPage

sealed class DetailsViewState {
    object Loading : DetailsViewState()
    data class LoadingError(val throwable: Throwable) : DetailsViewState()
    data class DetailsLoaded(val page: ProductDetailsPage) : DetailsViewState()
}