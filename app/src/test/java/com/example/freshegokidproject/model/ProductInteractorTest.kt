package com.example.freshegokidproject.model

import com.example.freshegokidproject.rules.LogRule
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
    @get:Rule
    val logRule = LogRule()

    @MockK
    private lateinit var repository: ProductRepository
    @MockK
    private lateinit var mockListPageObservable: Observable<ProductListPage>
    @MockK
    private lateinit var mockDetailsPageObservable: Observable<ProductDetailsPage>
    @MockK
    private lateinit var mockHomePageObservable: Observable<HomePage>

    private lateinit var interactor: ProductInteractor

    private val listPageObserver = TestObserver<ProductListPage>()
    private val listPageObservable = Observable.just(ProductListPage())
    private val detailsPageObserver = TestObserver<ProductDetailsPage>()
    private val detailsPageObservable = Observable.just(ProductDetailsPage())
    private val homePageObserver = TestObserver<HomePage>()
    private val homePageObservable = Observable.just(HomePage())

    @Before
    fun setUp() {
        interactor = ProductInteractor(repository)
        setupListPageObservable()
        setupDetailsPageObservable()
        setupQueriesForSearchResults()
        setupUrlsForDetailsUrls()
        setupHomePageObservable()
    }

    @Test
    fun `GIVEN query is one letter THEN getPageObservableWithSearchResultsByQuery returns mock observable`() {
        val actual = interactor.getPageObservableWithSearchResultsByQuery("v")
        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        assertEquals("did not equal mock observable", mockListPageObservable, actual)
    }

    @Test
    fun `GIVEN query is one word THEN getPageObservableWithSearchResultsByQuery returns mock observable`() {
        val actual = interactor.getPageObservableWithSearchResultsByQuery("valid")
        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        assertEquals("did not equal mock observable", mockListPageObservable, actual)
    }

    @Test
    fun `GIVEN query is more than one word THEN getPageObservableWithSearchResultsByQuery returns mock observable`() {
        val actual = interactor.getPageObservableWithSearchResultsByQuery("valid query")
        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        assertEquals("did not equal mock observable", mockListPageObservable, actual)
    }

    @Test
    fun `GIVEN query is empty THEN getPageObservableWithSearchResultsByQuery returns empty observable`() {
        val actual = interactor.getPageObservableWithSearchResultsByQuery("")
        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        listPageObserver.assertSubscribed()
        listPageObserver.assertEmpty()
        assertNotEquals("did equal mock observable", mockListPageObservable, actual)
    }

    @Test
    fun `GIVEN query is blank THEN getPageObservableWithSearchResultsByQuery returns empty observable`() {
        val actual = interactor.getPageObservableWithSearchResultsByQuery(" ")
        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        listPageObserver.assertSubscribed()
        listPageObserver.assertEmpty()
        assertNotEquals("did equal mock observable", mockListPageObservable, actual)
    }

    @Test
    fun `GIVEN detailsUrl is valid THEN getPageObservableWithDetailsByDetailsUrl returns mock observable`() {
        val actual = interactor.getPageObservableWithDetailsByDetailsUrl("valid")
        actual.subscribe(detailsPageObserver)

        detailsPageObserver.assertNoErrors()
        assertEquals("did not equal mock observable", mockDetailsPageObservable, actual)
    }

    @Test
    fun `GIVEN detailsUrl is empty THEN getPageObservableWithDetailsByDetailsUrl returns empty observable`() {
        val actual = interactor.getPageObservableWithDetailsByDetailsUrl("")
        actual.subscribe(detailsPageObserver)

        detailsPageObserver.assertNoErrors()
        detailsPageObserver.assertSubscribed()
        detailsPageObserver.assertEmpty()
        assertNotEquals("did equal mock observable", mockDetailsPageObservable, actual)
    }

    @Test
    fun `GIVEN detailsUrl is blank THEN getPageObservableWithDetailsByDetailsUrl returns empty observable`() {
        val actual = interactor.getPageObservableWithDetailsByDetailsUrl(" ")
        actual.subscribe(detailsPageObserver)

        detailsPageObserver.assertNoErrors()
        detailsPageObserver.assertSubscribed()
        detailsPageObserver.assertEmpty()
        assertNotEquals("did equal mock observable", mockDetailsPageObservable, actual)
    }

    @Test
    fun `GIVEN getPageObservableWithHomeBannerAndHomeProducts THEN returns mock observable`() {
        val actual = interactor.getPageObservableWithHomeBannerAndHomeProducts()
        actual.subscribe(homePageObserver)

        homePageObserver.assertNoErrors()
        assertEquals("did not equal mock observable", mockHomePageObservable, actual)
    }

    private fun setupListPageObservable() {
        every { mockListPageObservable.subscribeOn(any()) }.returns(mockListPageObservable)
        every { mockListPageObservable.doOnNext(any()) }.returns(mockListPageObservable)
        every { mockListPageObservable.observeOn(any()) }.returns(mockListPageObservable)
        every { mockListPageObservable.subscribe(listPageObserver) }.returns(Unit)
    }

    private fun setupQueriesForSearchResults() {
        every { repository.fetchSearchResultsByQuery("v") }.returns(mockListPageObservable)
        every { repository.fetchSearchResultsByQuery("valid") }.returns(mockListPageObservable)
        every { repository.fetchSearchResultsByQuery("valid query") }.returns(mockListPageObservable)
        every { repository.fetchSearchResultsByQuery("") }.returns(listPageObservable)
        every { repository.fetchSearchResultsByQuery(" ") }.returns(listPageObservable)
    }

    private fun setupDetailsPageObservable() {
        every { mockDetailsPageObservable.subscribeOn(any()) }.returns(mockDetailsPageObservable)
        every { mockDetailsPageObservable.doOnNext(any()) }.returns(mockDetailsPageObservable)
        every { mockDetailsPageObservable.observeOn(any()) }.returns(mockDetailsPageObservable)
        every { mockDetailsPageObservable.subscribe(detailsPageObserver) }.returns(Unit)
    }

    private fun setupUrlsForDetailsUrls() {
        every { repository.fetchDetailsByDetailsUrl("valid") }.returns(mockDetailsPageObservable)
        every { repository.fetchDetailsByDetailsUrl("")}.returns(detailsPageObservable)
        every { repository.fetchDetailsByDetailsUrl(" ")}.returns(detailsPageObservable)
    }

    private fun setupHomePageObservable() {
        every { mockHomePageObservable.subscribeOn(any()) }.returns(mockHomePageObservable)
        every { mockHomePageObservable.doOnNext(any()) }.returns(mockHomePageObservable)
        every { mockHomePageObservable.observeOn(any()) }.returns(mockHomePageObservable)
        every { mockHomePageObservable.subscribe(homePageObserver) }.returns(Unit)
        every { repository.fetchHomeBannerAndProducts() }.returns(mockHomePageObservable)
    }
}