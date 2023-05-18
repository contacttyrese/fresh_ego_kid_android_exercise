package com.example.freshegokidproject.model

import com.example.freshegokidproject.helpers.InteractorHelper
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

class ListInteractorTest {
    @get:Rule
    val rule = MockKRule(this)
    @get:Rule
    val schedulerRule = RxSchedulerRule()
    @get:Rule
    val logRule = LogRule()

    @MockK
    private lateinit var repository: ListRepository
    @MockK
    private lateinit var mockListPageObservable: Observable<ProductListPage>
    @MockK
    private lateinit var helper: InteractorHelper

    private lateinit var interactor: ListInteractor

    private val listPageObserver = TestObserver<ProductListPage>()
    private val listPageObservable = Observable.just(ProductListPage())

    @Before
    fun setUp() {
        interactor = ListInteractor(repository, helper)
        setupListPageObservable()
        setupQueriesForSearchResults()
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
}