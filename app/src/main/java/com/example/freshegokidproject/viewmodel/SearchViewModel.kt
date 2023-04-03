package com.example.freshegokidproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.freshegokidproject.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

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

    fun searchForProductsWithQueryViaHtmlService(query: String): Unit {
        var productListService = ProductListRetroFit.createProductListService()
        productListService.getProductsByQuery(query)
            .subscribeOn(Schedulers.io())
            .doOnError(Consumer {
                Log.e("list_service_error", "getRetroFitToWork: error", it)
            })
            .doOnSuccess {
                var imageUrl = it.imageUrl?.replace("{width}", "360")
                Log.i("image_url_raw", "print image raw url: ${it.imageUrl}")
                it.imageUrl = "https:$imageUrl"

                var host = ProductListService.API_URL.subSequence(0, ProductListService.API_URL.length-1)
                Log.i("details_url_raw", "print details raw url: ${it.detailsUrl}")
                it.detailsUrl = "${host}${it.detailsUrl}"
            }
            .onTerminateDetach()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
            printProductSearchResult(it)
        })
    }

    fun printProductSearchResult(product: ProductSearchResult): Unit {
            Log.i("search_result_title","product title: ${product.title}")
            Log.i("search_result_price", "product price: ${product.price}")
            Log.i("search_result_image", "product image url: ${product.imageUrl}")
            Log.i("search_result_details", "product details url: ${product.detailsUrl}")
    }
}