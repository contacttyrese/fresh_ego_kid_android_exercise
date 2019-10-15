package com.example.freshegokidproject.model

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer

class ProductObservable : Observable<Product>() {

    override fun subscribeActual(observer: Observer<in Product>?) {
        subscribeActual(observer)
    }

//    var mKey: String = ""
//    var mDescription: String = ""
//    var mPrice: String = ""

//    val mProduct = Product("", "", "")

//    override fun registerObserver(observer: Product?) {
//        super.registerObserver(observer)
//    }

//    fun something() {
//        mKey = product.key
//        mDescription = product.description
//        mPrice = product.price
//        setChanged()
//        notifyObservers(product)
//    }
}