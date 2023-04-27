package com.example.freshegokidproject.model

import pl.droidsonroids.jspoon.annotation.Selector

class ProductListPage {
    @Selector(".grid-product__content")
//    lateinit var productSearchResults: List<ProductSearchResult>
    var productSearchResults = listOf<ProductSearchResult>()
}