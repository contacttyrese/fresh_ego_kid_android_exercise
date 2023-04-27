package com.example.freshegokidproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.freshegokidproject.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val interactor: ProductInteractor,
    private val disposables: CompositeDisposable
    ) : ViewModel() {
//    private var productsFound: ArrayList<Product> = ArrayList()
    //    private val observer = ProductSearchResultObserver()
//    private val observer = ProductListPageObserver()
    val userActionSubject = PublishSubject.create<SearchUserAction>()
    private val _viewState = MutableLiveData<SearchViewState>()
    val viewState : LiveData<SearchViewState>
        get() = _viewState
    val userAction = MutableLiveData<SearchUserAction>()
//    val userAction : MutableLiveData<SearchUserAction>
//        get() = _userAction
    private val _searchResultsMutableLiveData = MutableLiveData<ProductListPage>()
    val searchResultsLiveData: LiveData<ProductListPage>
        get() = _searchResultsMutableLiveData

    init {
//        _userAction.value = SearchUserAction.AwaitingInput
//        userAction.postValue(SearchUserAction.AwaitingInput)
//        _viewState.value = SearchViewState.Loading
//        _viewState.postValue(SearchViewState.Loading)
//
//        disposables.add(
//            interactor.getProductsObservable()
//                .subscribe({ list ->
//                    processProducts(list)
//                }, { throwable ->
//                    processThrowable(throwable)
//                })
//        )

        disposables.add(
            userActionSubject
                .subscribeOn(Schedulers.io())
                .subscribe({ searchUserAction ->
                    when (searchUserAction) {
                        SearchUserAction.AwaitingInput -> {
                            Log.i("user_action_await", "awaiting input")
                        }
                        is SearchUserAction.UserTyping -> {
                            Log.i("user_action_typing", "user typing: ${searchUserAction.input}")
//                            _viewState.postValue(SearchViewState.Loading)
                        }
                        is SearchUserAction.QuerySubmittedError -> {
                            Log.e("user_action_error", "user query error")
                            processThrowable(java.lang.RuntimeException("user query error"))
                        }
                        is SearchUserAction.QuerySubmittedSuccess -> {
                            Log.i("user_action_success", "user query success")
                            _viewState.postValue(SearchViewState.Loading)
                            disposables.add(
                                interactor.getPageObservableWithSearchResultsByQuery(searchUserAction.query)
                                    .subscribe({ list ->
                                        processSearchResults(list)
                                    }, { throwable ->
                                        processThrowable(throwable)
                                    })
                            )
                        }
                    }
                }, { throwable ->
                    processThrowable(throwable)
                })
        )
    }

//    fun getProductsFoundByQuery(query: String, products: ArrayList<Product>) : ArrayList<Product> {
//        productsFound
//        for (product in products) {
//            if (product.title.contains(query, true) ||
//                product.description.contains(query, true)) {
//                productsFound.add(product)
//            }
//        }
//        return productsFound
//    }

//    fun searchForProductsWithQueryViaHtmlService(query: String): ArrayList<Product> {
//        var productListService = ProductListRetroFit.createProductListService()
//        productListService.getProductsByKeyword(query)
//            .subscribeOn(Schedulers.io())
//            .doOnError(Consumer {
//                Log.e("list_service_error", "list service error querying for products", it)
//            })
//            .doOnNext {
//                var imageUrl = it.imageUrl?.replace("{width}", "360")
//                Log.i("image_url_raw", "print image raw url: ${it.imageUrl}")
//                it.imageUrl = "https:$imageUrl"
//
//                var host = ProductListService.API_URL.subSequence(0, ProductListService.API_URL.length-1)
//                Log.i("details_url_raw", "print details raw url: ${it.detailsUrl}")
//                it.detailsUrl = "${host}${it.detailsUrl}"
//
//                it.detailsUrl?.split("products/","?", ignoreCase =  true, limit =  0)?.forEach {
//                    Log.i("test", "test string for key: $it")
//                }
//                val key = it.detailsUrl?.split("products/","?", ignoreCase =  true, limit =  0)!![1]
//                Log.i("search_result_key", "product key $key")
//                val product = Product(key, it.title!!, it.price!!, it.detailsUrl!!, it.imageUrl!!)
//                productsFound.add(product)
//            }
//            .onTerminateDetach()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(Consumer {
//                printProductSearchResult(it)
//            })

//        val productListServiceObservable = productListService.getProductsByQuery(query)
//            .subscribeOn(Schedulers.io())
//            .filter { it ->
//                Log.i("filter_thread", "executing on thread = ${Thread.currentThread().name}")
//
//                var imageUrl = it.imageUrl?.replace("{width}", "360")
//                Log.i("image_url_raw", "print image raw url: ${it.imageUrl}")
//                it.imageUrl = "https:$imageUrl"
//
//                var host = ProductListService.API_URL.subSequence(0, ProductListService.API_URL.length-1)
//                Log.i("details_url_raw", "print details raw url: ${it.detailsUrl}")
//                it.detailsUrl = "${host}${it.detailsUrl}"
//
//                it.imageUrl != null
//            }
//            .onTerminateDetach()
//            .observeOn(AndroidSchedulers.mainThread())
//        productListServiceObservable.subscribe(observer)
//
//        val productListServiceObservable = productListService.getProductsByKeyword(query)
//            .subscribeOn(Schedulers.io())
//            .filter { it ->
//                var emptyUrl = false
//                it.productSearchResults.forEach {
//                    Log.i("filter_thread", "executing on thread = ${Thread.currentThread().name}")
//
//                    var imageUrl = it.imageUrl?.replace("{width}", "360")
//                    Log.i("image_url_raw", "print image raw url: ${it.imageUrl}")
//                    it.imageUrl = "https:$imageUrl"
//
//                    var host = ProductListService.API_URL.subSequence(0, ProductListService.API_URL.length-1)
//                    Log.i("details_url_raw", "print details raw url: ${it.detailsUrl}")
//                    it.detailsUrl = "${host}${it.detailsUrl}"
//                    emptyUrl = it.imageUrl != null
//                }
//                emptyUrl
//
//            }
//            .onTerminateDetach()
//            .observeOn(AndroidSchedulers.mainThread())
//        productListServiceObservable.subscribe(observer)
//        return observer.getProducts()
//    }

//    fun printProductSearchResult(product: ProductSearchResult): Unit {
//            Log.i("search_result_title","product title: ${product.title}")
//            Log.i("search_result_price", "product price: ${product.price}")
//            Log.i("search_result_image", "product image url: ${product.imageUrl}")
//            Log.i("search_result_details", "product details url: ${product.detailsUrl}")
//    }

    fun clearDisposable() {
//        disposables.add(observer.getDisposables())
        // soft clear
//        observer.getDisposables().clear()
//        disposables.clear()
        // hard clear
        disposables.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        clearDisposable()
    }

    private fun processSearchResults(listPage: ProductListPage) {
//        Log.i("process_product_sub", "products size is ${listPage.size}")
        Log.i("process_result_sub", "size of search results is ${listPage.productSearchResults.size}")
        _searchResultsMutableLiveData.postValue(listPage)
        _viewState.postValue(SearchViewState.ProductLoaded(listPage))
    }

    private fun processThrowable(throwable: Throwable) {
        Log.e("process_product_error", "product processing error $throwable")
        throwable.printStackTrace()
        _viewState.postValue(SearchViewState.ProductLoadError(throwable))
    }
}