package com.example.freshegokidproject.model

import com.squareup.moshi.Json

data class ProductImage constructor(
    @field:Json(name = "id")
    var key: String? = null,
    @field:Json(name = "src")
    var src: String? = null
)