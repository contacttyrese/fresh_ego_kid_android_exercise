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

class DetailsInteractorTest {
    @get:Rule
    val rule = MockKRule(this)
    @get:Rule
    val schedulerRule = RxSchedulerRule()
    @get:Rule
    val logRule = LogRule()

    @MockK
    private lateinit var repository: DetailsRepository
    @MockK
    private lateinit var mockDetailsPageObservable: Observable<ProductDetailsPage>

    private lateinit var interactor: DetailsInteractor

    private val detailsPageObserver = TestObserver<ProductDetailsPage>()
    private val detailsPageObservable = Observable.just(ProductDetailsPage())

    @Before
    fun setUp() {
        interactor = DetailsInteractor(repository)
        setupDetailsPageObservable()
        setupUrlsForDetailsUrls()
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
}