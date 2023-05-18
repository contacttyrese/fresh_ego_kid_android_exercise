package com.example.freshegokidproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.freshegokidproject.model.HomePage
import com.example.freshegokidproject.model.HomeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactor: HomeInteractor
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState>
        get() = _viewState

    init {
        _viewState.postValue(MainViewState.LoadingBannerAndProducts)
        disposables.add(
            interactor.getPageObservableWithHomeBannerAndHomeProducts()
                .subscribe({ page ->
                    processBannerAndPhotos(page)
                }, { throwable ->
                    processThrowable(throwable)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    private fun processBannerAndPhotos(page: HomePage) {
        Log.i("mainviewmodel_loaded", "banner and products loaded")
        Log.i("print_test", "print banner: ${page.bannerUrl}")
        _viewState.postValue(page.bannerUrl?.let { bannerUrl ->
            MainViewState.LoadingSuccess(bannerUrl, page.products)
        })
    }

    private fun processThrowable(throwable: Throwable) {
        Log.e("mainviewmodel_error", "banner and products loading error")
        _viewState.postValue(MainViewState.LoadingError(throwable))
    }
}