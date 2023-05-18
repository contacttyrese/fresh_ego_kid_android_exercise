package com.example.freshegokidproject.model

import com.example.freshegokidproject.network.HomeService
import com.example.freshegokidproject.network.ProductDetailsService
import com.example.freshegokidproject.network.ProductListService
import com.example.freshegokidproject.rules.LogRule
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsRepositoryTest {
    @get:Rule
    val logRule = LogRule()

    private val detailsService = mockk<ProductDetailsService>()
    private val detailsPageObservable = mockk<Observable<ProductDetailsPage>>()
    private val detailsPageObserver = TestObserver<ProductDetailsPage>()

    private val repository = DetailsRepository(detailsService)

    @Before
    fun setUp() {
        setupPagesResponses()
        setupSubscribers()
    }

    @Test
    fun `GIVEN path is valid THEN fetchDetailsByDetailsUrl should return mock observable`() {
        val actual = repository.fetchDetailsByDetailsUrl("a")

        actual.subscribe(detailsPageObserver)

        detailsPageObserver.assertNoErrors()
        assertEquals("actual did not equal mock obserable", detailsPageObservable, actual)
    }

    @Test
    fun `GIVEN path is empty THEN fetchDetailsByDetailsUrl should return empty details`() {
        val actual = repository.fetchDetailsByDetailsUrl("")

        actual.subscribe(detailsPageObserver)

        detailsPageObserver.assertNoErrors()
        detailsPageObserver.assertComplete()
        detailsPageObserver.assertSubscribed()
        val details = detailsPageObserver.values()[0].details
        assertNotEquals("actual did equal mock obserable", detailsPageObservable, actual)
        assertNull(details.key)
        assertNull(details.description)
        assertNull(details.title)
        assertNull(details.images)
        assertNull(details.variants)
    }

    @Test
    fun `GIVEN path is blank THEN fetchDetailsByDetailsUrl should return empty details`() {
        val actual = repository.fetchDetailsByDetailsUrl(" ")

        actual.subscribe(detailsPageObserver)

        detailsPageObserver.assertNoErrors()
        detailsPageObserver.assertComplete()
        detailsPageObserver.assertSubscribed()
        val details = detailsPageObserver.values()[0].details
        assertNotEquals("actual did equal mock obserable", detailsPageObservable, actual)
        assertNull(details.key)
        assertNull(details.description)
        assertNull(details.title)
        assertNull(details.images)
        assertNull(details.variants)
    }

    private fun setupPagesResponses() {
        every { detailsService.getProductDetailsPageWithProductDetailsByPath(any()) }.returns(detailsPageObservable)
    }

    private fun setupSubscribers() {
        every { detailsPageObservable.subscribe(detailsPageObserver) }.returns(Unit)
    }
}