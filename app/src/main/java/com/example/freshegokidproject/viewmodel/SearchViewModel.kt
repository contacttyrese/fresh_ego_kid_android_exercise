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
    private val interactor: ListInteractor,
    private val disposables: CompositeDisposable
    ) : ViewModel() {
    val userActionSubject = PublishSubject.create<SearchUserAction>()
    private val _viewState = MutableLiveData<SearchViewState>()
    val viewState : LiveData<SearchViewState>
        get() = _viewState
    val userAction = MutableLiveData<SearchUserAction>()

    init {
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
                                    .subscribe({ page ->
                                        processSearchResults(page)
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

    fun clearDisposable() {
        // soft clear
//        disposables.clear()
        // hard clear
        disposables.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        clearDisposable()
    }

    private fun processSearchResults(listPage: ProductListPage) {
        Log.i("process_result_sub", "size of search results is ${listPage.searchResults.size}")
        _viewState.postValue(SearchViewState.ProductLoaded(listPage))
    }

    private fun processThrowable(throwable: Throwable) {
        Log.e("process_product_error", "product processing error $throwable")
        throwable.printStackTrace()
        _viewState.postValue(SearchViewState.ProductLoadError(throwable))
    }
}