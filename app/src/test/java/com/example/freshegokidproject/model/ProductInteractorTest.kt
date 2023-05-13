package com.example.freshegokidproject.model

import com.example.freshegokidproject.rules.RxSchedulerRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test

class ProductInteractorTest {
    @get:Rule
    val rule = MockKRule(this)
    @get:Rule
    val schedulerRule = RxSchedulerRule()

    @MockK
    private lateinit var repository: ProductRepository
    @MockK
    private lateinit var mockListPageObservable: Observable<ProductListPage>
    @MockK
    private lateinit var detailsPageObservable: Observable<ProductDetailsPage>
    @MockK
    private lateinit var homePageObservable: Observable<HomePage>

    private lateinit var interactor: ProductInteractor

    private val listPageObserver = TestObserver<ProductListPage>()
    private val listPageObservable = Observable.just(ProductListPage())

    @Before
    fun setUp() {
        interactor = ProductInteractor(repository)
        every { repository.fetchSearchResultsByQuery("valid") }.returns(mockListPageObservable)
        every { repository.fetchSearchResultsByQuery("") }.returns(listPageObservable)
        every { repository.fetchSearchResultsByQuery(" ") }.returns(listPageObservable)
        setupListPageObservable()
    }

    @Test
    fun `GIVEN query is valid THEN getPageObservableWithSearchResultsByQuery returns mock observable`() {
        val actual = interactor.getPageObservableWithSearchResultsByQuery("valid")
        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        assertEquals("did not equal mock observable", mockListPageObservable, actual)
    }

    @Test
    fun `GIVEN query is empty THEN getPageObservableWithSearchResultsByQuery returns empty observable`() {
        val actual = interactor.getPageObservableWithSearchResultsByQuery("")
        actual.subscribe(listPageObserver)

//        assertNotEquals("did equal mock ob")
    }

    @Test
    fun `GIVEN query is blank THEN getPageObservableWithSearchResultsByQuery returns empty observable`() {

    }

    @Test
    fun getPageObservableWithDetailsByDetailsUrl() {
    }

    @Test
    fun getPageObservableWithHomeBannerAndHomeProducts() {
    }

    private fun setupListPageObservable() {
        every { mockListPageObservable.subscribeOn(any()) }.returns(mockListPageObservable)
        every { mockListPageObservable.doOnNext(any()) }.returns(mockListPageObservable)
        every { mockListPageObservable.observeOn(any()) }.returns(mockListPageObservable)
        every { mockListPageObservable.subscribe(listPageObserver) }.returns(Unit)
    }
}