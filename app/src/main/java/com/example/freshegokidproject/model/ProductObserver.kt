//package com.example.freshegokidproject.model
//
//import io.reactivex.rxjava3.core.Observer
//import io.reactivex.rxjava3.disposables.Disposable
//
//
//class ProductObserver : Observer<Product> {
//    override fun onComplete() {
//        println("onComplete")
//    }
//
//    override fun onSubscribe(d: Disposable?) {
//        println("******onSubscribe*******")
//    }
//
//    override fun onNext(t: Product?) {
//        println("***onNext product is: ${t?.key}***")
//    }
//
//    override fun onError(e: Throwable?) {
//        e?.printStackTrace()
//    }
//
//}