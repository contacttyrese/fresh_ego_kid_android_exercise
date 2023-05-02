package com.example.freshegokidproject.model

import com.example.freshegokidproject.network.HomeService
import com.example.freshegokidproject.network.ProductDetailsService
import com.example.freshegokidproject.network.ProductListService
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {
    private val listService = mockk<ProductListService>()
    private val detailsService = mockk<ProductDetailsService>()
    private val homeService = mockk<HomeService>()
    private val listPageObservable = mockk<Observable<ProductListPage>>()
    private val detailsPageObservable = mockk<Observable<ProductDetailsPage>>()
    private val homePageObservable = mockk<Observable<HomePage>>()

    private val repository = ProductRepository(listService, detailsService, homeService)

    @Before
    fun setUp() {
        every { listService.getProductListPageWithSearchResultsByQuery(any()) }.returns(listPageObservable)
        every { detailsService.getProductDetailsPageWithProductDetailsByPath(any()) }.returns(detailsPageObservable)
        every { homeService.getHomepage() }.returns(homePageObservable)
    }

    @Test
    fun `GIVEN query is valid THEN fetchSearchResultsByQuery should return mock observable`() {
        val actual = repository.fetchSearchResultsByQuery("a")

        assertEquals("actual did not equal mock observable", listPageObservable, actual)
    }

    @Test
    fun `GIVEN query is empty THEN fetchSearchResultsByQuery should return observable`() {
        val actual = repository.fetchSearchResultsByQuery("")

        assertNotEquals("actual did equal mock observable", listPageObservable, actual)
    }

    @Test
    fun `GIVEN query is blank THEN fetchSearchResultsByQuery should return observable`() {
        val actual = repository.fetchSearchResultsByQuery("  ")

        assertNotEquals("actual did equal mock observable", listPageObservable, actual)
    }

    @Test
    fun `GIVEN path is valid THEN fetchDetailsByDetailsUrl should return mock observable`() {
        val actual = repository.fetchDetailsByDetailsUrl("a")

        assertEquals("actual did not equal mock obserable", detailsPageObservable, actual)
    }

    @Test
    fun `GIVEN path is empty THEN fetchDetailsByDetailsUrl should return mock observable`() {
        val actual = repository.fetchDetailsByDetailsUrl("")

        assertNotEquals("actual did equal mock obserable", detailsPageObservable, actual)
    }

    @Test
    fun `GIVEN path is blank THEN fetchDetailsByDetailsUrl should return mock observable`() {
        val actual = repository.fetchDetailsByDetailsUrl(" ")

        assertNotEquals("actual did equal mock obserable", detailsPageObservable, actual)
    }

    @Test
    fun `GIVEN fetchHomeBannerAndProducts THEN should return mock observable`() {
        val actual = repository.fetchHomeBannerAndProducts()

        assertEquals("actual did not mock observable", homePageObservable, actual)
    }
}