package com.example.freshegokidproject.model

import com.squareup.moshi.Json

data class ProductDetails constructor(
    @field:Json(name = "id")
    var key: String? = null,
    @field:Json(name = "title")
    val title: String? = null,
    @field:Json(name = "variants")
    var variants: List<ProductVariant>? = null,
    @field:Json(name = "body_html")
    var description: String? = null,
    @field:Json(name = "images")
    var images: List<ProductImage>? = null
    )