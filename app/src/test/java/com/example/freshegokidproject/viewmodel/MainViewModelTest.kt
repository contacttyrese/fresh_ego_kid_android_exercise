package com.example.freshegokidproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.freshegokidproject.model.HomePage
import com.example.freshegokidproject.model.HomeInteractor
import com.example.freshegokidproject.model.SearchResult
import com.example.freshegokidproject.rules.LogRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    val logRule = LogRule()

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    private val interactor = mockk<HomeInteractor>()
    private val mockObservable = mockk<Observable<HomePage>>()
    private val homePage = mockk<HomePage>()
    private val homeObservable = Observable.just(homePage)
    private val mockObserver = mockk<Observer<MainViewState>>()
    private val disposable = mockk<CompositeDisposable>()
    private val searchResult = mockk<SearchResult>()
    private val list = listOf(searchResult)

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        every { homePage.bannerUrl }.returns("test")
        every { homePage.products }.returns(list)
        every { mockObserver.onChanged(MainViewState.LoadingBannerAndProducts) }.returns(Unit)
        every { mockObserver.onChanged(MainViewState.LoadingSuccess("test", list)) }.returns(Unit)
    }

    @Test
    fun `GIVEN interactor returns mock THEN state change is loading banner and products`() {
        every { interactor.getPageObservableWithHomeBannerAndHomeProducts() }.returns(mockObservable)
        every { mockObservable.subscribe(any(), any()) }.returns(disposable)

        mainViewModel = spyk(MainViewModel(interactor), recordPrivateCalls = true)
        mainViewModel.viewState.observeForever(mockObserver)

        verify { mockObserver.onChanged(MainViewState.LoadingBannerAndProducts) }
    }

    @Test
    fun `GIVEN interactor returns observable THEN state change is loading success`() {
        every { interactor.getPageObservableWithHomeBannerAndHomeProducts() }.returns(homeObservable)

        mainViewModel = spyk(MainViewModel(interactor), recordPrivateCalls = true)
        mainViewModel.viewState.observeForever(mockObserver)

        verify { mockObserver.onChanged(MainViewState.LoadingSuccess("test", list)) }

    }

    @Test
    fun getViewState() {

    }

    @Test
    fun onCleared() {
    }
}