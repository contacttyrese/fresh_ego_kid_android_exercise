package com.example.freshegokidproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.freshegokidproject.model.DetailsInteractor
import com.example.freshegokidproject.model.ProductDetailsPage
import com.example.freshegokidproject.model.HomeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val interactor: DetailsInteractor,
    private val disposables: CompositeDisposable
) : ViewModel() {
    private lateinit var _detailsUrl: String
    private val _viewState = MutableLiveData<DetailsViewState>()
    val viewState: LiveData<DetailsViewState>
        get() = _viewState
    val userActionSubject = PublishSubject.create<DetailsUserAction>()

    init {
        disposables.add(
            userActionSubject
                .subscribeOn(Schedulers.io())
                .subscribe({ detailsUserAction ->
                    when (detailsUserAction) {
                        DetailsUserAction.AwaitingSelection -> {
                            Log.i("viewmodel_awaiting", "attempting to load details")
                            _viewState.postValue(DetailsViewState.Loading)
                            disposables.add(
                                interactor.getPageObservableWithDetailsByDetailsUrl(_detailsUrl)
                                    .subscribe({ page ->
                                        processDetails(page)
                                    }, { throwable ->
                                        processThrowable(throwable)
                                    })
                            )
                        }
                        DetailsUserAction.AddToCart -> Log.i("details_add_to", "adding product to bag")
                    }

                }, { throwable ->
                    _viewState.postValue(DetailsViewState.LoadingError(throwable))
                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun setRequiredProductDetails(detailsUrl: String) {
        _detailsUrl = detailsUrl
    }

    private fun processDetails(detailsPage: ProductDetailsPage) {
        Log.i("process_details_sub", "details loaded with description: ${detailsPage.details.description}")
        Log.i("process_details_sub", "details loaded with price: ${detailsPage.details.variants?.get(0)?.price}")
        Log.i("process_details_sub", "details loaded with image src: ${detailsPage.details.images?.get(0)?.src}")
        _viewState.postValue(DetailsViewState.DetailsLoaded(detailsPage))
    }

    private fun processThrowable(throwable: Throwable) {
        Log.e("process_product_error", "product processing error $throwable")
        throwable.printStackTrace()
        _viewState.postValue(DetailsViewState.LoadingError(throwable))
    }

}