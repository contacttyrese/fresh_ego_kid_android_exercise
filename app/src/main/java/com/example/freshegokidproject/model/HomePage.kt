package com.example.freshegokidproject.model

import pl.droidsonroids.jspoon.annotation.Selector

class HomePage {
//    @Selector(".hero__image-wrapper > img")
//    lateinit var banner: Banner

    @Selector(".grid-product__content")
    var products = listOf<SearchResult>()

}