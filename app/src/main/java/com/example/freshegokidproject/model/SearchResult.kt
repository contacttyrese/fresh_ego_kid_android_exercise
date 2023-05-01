package com.example.freshegokidproject.model

import pl.droidsonroids.jspoon.annotation.Selector

data class SearchResult constructor(
    var key: String? = null,

    @Selector(".grid-product__title")
    var title: String? = null,

    @Selector(".grid-product__price > .money")
    var price: String? = null,

    @Selector(value = ".lazyload", attr = "data-src")
    var imageUrl: String? = null,

    @Selector(value = ".grid-product__link", attr = "href")
    var detailsUrl: String? = null){
}