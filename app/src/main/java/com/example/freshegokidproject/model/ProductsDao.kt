package com.example.freshegokidproject.model

import android.content.Context
import android.content.res.AssetManager
import androidx.core.content.ContextCompat
import com.example.freshegokidproject.R
import org.json.JSONObject
import java.net.URL

class ProductsDao(val file: String, val context: Context) {
    val mProducts: List<Product> = getAllProducts()

    private fun getAllProducts(): ArrayList<Product> {
        val listOfProducts = ArrayList<Product>()
        val productsJson = JSONObject(context.assets.open(file).bufferedReader(Charsets.UTF_8).readText()).getJSONObject("products")
        for (numberOfProducts in 0 until productsJson.length()) {
            val productKey = productsJson.names().get(numberOfProducts).toString()
            val productJson = productsJson.getJSONObject(productKey)
            val productDescription = productJson.getString("description")
            val productPrice = productJson.getString("price")
            val productImage: URL? =
                this::class.java.getResource("/res/drawable/" + productKey + ".jpg")
            assert(productImage != null) { "product image was not found" }

            val productImageDrawable = when(productKey) {
                "fek_460" -> ContextCompat.getDrawable(context, R.drawable.fek_460)
                "fek_497" -> ContextCompat.getDrawable(context, R.drawable.fek_497)
                "fek_500" -> ContextCompat.getDrawable(context, R.drawable.fek_500)

                else -> null
            }
            assert(productImageDrawable != null)

            val product = Product(productKey, productDescription, productPrice, productImageDrawable!!)
            listOfProducts.add(numberOfProducts, product)
        }
        return listOfProducts
    }

}