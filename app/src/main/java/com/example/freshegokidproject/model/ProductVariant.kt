package com.example.freshegokidproject.model

import com.squareup.moshi.Json

data class ProductVariant constructor(
    @field:Json(name = "id")
    var key: String? = null,
    @field:Json(name = "price")
    var price: String? = null
)