package com.example.freshegokidproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.freshegokidproject.model.Product
import com.example.freshegokidproject.model.ProductDao
import java.io.BufferedReader

class ProductViewModel constructor(private val reader: BufferedReader) : ViewModel() {
    var lst = MutableLiveData<ArrayList<Product>>()
    var newList = ArrayList<Product>()

    fun add(product: Product) {
        newList.add(product)
        lst.value = newList
    }

    fun remove(product: Product) {
        newList.remove(product)
        lst.value = newList
    }

    fun getAllProducts() : ArrayList<Product> {
        val dao = ProductDao(reader)
        newList = dao.mProducts
        lst.value = newList
        return newList
    }

//    fun getProductByKey(productKey: String): Product {
//        return mProducts.getValue(productKey)
//    }
}