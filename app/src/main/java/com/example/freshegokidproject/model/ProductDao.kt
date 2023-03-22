package com.example.freshegokidproject.model

import android.content.Context
import androidx.collection.ArrayMap
import org.json.JSONObject

class ProductDao(val file: String, val context: Context) {
    val mProducts: Map<String, Product> = getAllProducts()

    private fun getAllProducts(): Map<String, Product> {
        val mapOfProducts = ArrayMap<String, Product>()
        val reader = context.assets.open(file).bufferedReader(Charsets.UTF_8)
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
            mapOfProducts.put(productKey, product)
        }
        return mapOfProducts
    }

    fun getProductByKey(productKey: String): Product {
        return mProducts.getValue(productKey)
    }

}