package com.example.freshegokidproject.model

import org.json.JSONObject
import java.io.BufferedReader

class ProductDao(private val reader: BufferedReader) {
    val mProducts: ArrayList<Product> = readProductsFromJson()

    private fun readProductsFromJson(): ArrayList<Product> {
        val listOfProducts = ArrayList<Product>()
        val jsonString = reader.use {
            it.readText()
        }
        val productsJson = JSONObject(jsonString).getJSONObject("products")
        for (numberOfProducts in 0 until productsJson.length()) {
            val productKey = productsJson.names().get(numberOfProducts).toString()
            val productJson = productsJson.getJSONObject(productKey)
            val productTitle = productJson.getString("title")
            val productPrice = productJson.getString("price")
            val productDescription = productJson.getString("description")
            var isProductImageInResources =
                this::class.java.getResource("/res/drawable/$productKey.jpg") != null ||
                this::class.java.getResource("/res/drawable/$productKey.png") != null
            assert(isProductImageInResources) { "product image was not found" }

            val product = Product(productKey, productTitle, productPrice, productDescription)
            listOfProducts.add(product)
        }
        return listOfProducts
    }

}