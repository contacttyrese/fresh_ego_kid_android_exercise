package com.example.freshegokidproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.freshegokidproject.model.Product

class SearchActivityViewModel : ViewModel() {

    fun getProductsFoundByQuery(query: String, products: ArrayList<Product>) : ArrayList<Product> {
        var productsFound : ArrayList<Product> = ArrayList()
        for (product in products) {
            if (product.title.contains(query, true) ||
                product.description.contains(query, true)) {
                productsFound.add(product)
            }
        }
        return productsFound
    }
}