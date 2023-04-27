//package com.example.freshegokidproject.model
//
//import android.util.Log
//import io.reactivex.Observer
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.disposables.Disposable
//
//
//class ProductListPageObserver() : Observer<ProductListPage> {
//    private var products = ArrayList<Product>()
//    private var disposables: CompositeDisposable = CompositeDisposable()
//
//    override fun onSubscribe(d: Disposable) {
//        disposables.add(disposables)
//    }
//
//    override fun onNext(t: ProductListPage) {
//        t.productSearchResults.forEach {
//            it.detailsUrl?.split("products/","?", ignoreCase =  true, limit =  0)?.forEach {
//                Log.i("split_details_url", "potential string array for key: $it")
//            }
//            val key = it.detailsUrl?.split("products/","?", ignoreCase =  true, limit =  0)!![1]
//            Log.i("search_result_key", "product key $key")
//            Log.i("search_result_thread", "current thread is = ${Thread.currentThread().name}")
//            val product = Product(key, it.title!!, it.price!!, it.detailsUrl!!, it.imageUrl!!)
//            products.add(product)
//        }
//
//    }
//
//    override fun onError(e: Throwable) {
//        Log.e("list_service_error", "list service error querying for products", e)
//    }
//
//    override fun onComplete() {
//        Log.i("complete_observer", "completed observer")
//    }
//
//    fun getDisposables() : CompositeDisposable {
//        return disposables
//    }
//
//    fun getProducts() : ArrayList<Product> {
//        return products
//    }
//
//}