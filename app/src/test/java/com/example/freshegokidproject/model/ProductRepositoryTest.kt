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

class ProductRepositoryTest {
    @get:Rule
    val logRule = LogRule()

    private val listService = mockk<ProductListService>()
    private val detailsService = mockk<ProductDetailsService>()
    private val homeService = mockk<HomeService>()
    private val listPageObservable = mockk<Observable<ProductListPage>>()
    private val detailsPageObservable = mockk<Observable<ProductDetailsPage>>()
    private val homePageObservable = mockk<Observable<HomePage>>()
    private val listPageObserver = TestObserver<ProductListPage>()
    private val detailsPageObserver = TestObserver<ProductDetailsPage>()
    private val homePageObserver = TestObserver<HomePage>()

    private val repository = ProductRepository(listService, detailsService, homeService)

    @Before
    fun setUp() {
        setupPagesResponses()
        setupSubscribers()
    }

    @Test
    fun `GIVEN query is one letter THEN fetchSearchResultsByQuery should return mock observable`() {
        val actual = repository.fetchSearchResultsByQuery("a")

        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        assertEquals("actual did not equal mock observable", listPageObservable, actual)
    }

    @Test
    fun `GIVEN query is one word THEN fetchSearchResultsByQuery should return mock observable`() {
        val actual = repository.fetchSearchResultsByQuery("hat")

        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        assertEquals("actual did not equal mock observable", listPageObservable, actual)
    }

    @Test
    fun `GIVEN query is more than one word THEN fetchSearchResultsByQuery should return mock observable`() {
        val actual = repository.fetchSearchResultsByQuery("blue hat")

        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        assertEquals("actual did not equal mock observable", listPageObservable, actual)
    }

    @Test
    fun `GIVEN query is a number THEN fetchSearchResultsByQuery should return empty search results`() {
        val actual = repository.fetchSearchResultsByQuery("404")

        actual.subscribe(listPageObserver)

        listPageObserver.assertComplete()
        listPageObserver.assertNoErrors()
        listPageObserver.assertSubscribed()
        listPageObserver.assertValue { page ->
            page.searchResults.isEmpty()
        }
        assertNotEquals("actual did equal mock observable", listPageObservable, actual)
    }

    @Test
    fun `GIVEN query is empty THEN fetchSearchResultsByQuery should return empty search results`() {
        val actual = repository.fetchSearchResultsByQuery("")

        actual.subscribe(listPageObserver)

        listPageObserver.assertComplete()
        listPageObserver.assertNoErrors()
        listPageObserver.assertSubscribed()
        listPageObserver.assertValue { page ->
            page.searchResults.isEmpty()
        }
        assertNotEquals("actual did equal mock observable", listPageObservable, actual)
    }

    @Test
    fun `GIVEN query is blank THEN fetchSearchResultsByQuery should return empty observable`() {
        val actual = repository.fetchSearchResultsByQuery("  ")

        actual.subscribe(listPageObserver)

        listPageObserver.assertComplete()
        listPageObserver.assertNoErrors()
        listPageObserver.assertSubscribed()
        listPageObserver.assertValue { page ->
            page.searchResults.isEmpty()
        }
        assertNotEquals("actual did equal mock observable", listPageObservable, actual)
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

    @Test
    fun `GIVEN fetchHomeBannerAndProducts THEN should return mock observable`() {
        val actual = repository.fetchHomeBannerAndProducts()

        actual.subscribe(homePageObserver)

        homePageObserver.assertNoErrors()
        assertEquals("actual did not mock observable", homePageObservable, actual)
    }

    private fun setupPagesResponses() {
        every { listService.getProductListPageWithSearchResultsByQuery(any()) }.returns(listPageObservable)
        every { detailsService.getProductDetailsPageWithProductDetailsByPath(any()) }.returns(detailsPageObservable)
        every { homeService.getHomepage() }.returns(homePageObservable)
    }

    private fun setupSubscribers() {
        every { listPageObservable.subscribe(listPageObserver) }.returns(Unit)
        every { detailsPageObservable.subscribe(detailsPageObserver) }.returns(Unit)
        every { homePageObservable.subscribe(homePageObserver) }.returns(Unit)
    }
}