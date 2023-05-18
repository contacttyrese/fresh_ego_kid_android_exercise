package com.example.freshegokidproject.model

import com.example.freshegokidproject.helpers.RepositoryHelper
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

class ListRepositoryTest {
    private val listService = mockk<ProductListService>()
    private val listPageObservable = mockk<Observable<ProductListPage>>()
    private val listPageObserver = TestObserver<ProductListPage>()
    private val helper = mockk<RepositoryHelper>()

    private val repository = ListRepository(listService, helper)

    @Before
    fun setUp() {
        setupPagesResponses()
        setupSubscribers()
    }

    @Test
    fun `GIVEN query is valid THEN fetchSearchResultsByQuery should return mock observable`() {
        every { helper.checkQueryIsValid(any()) }.returns(true)
        val actual = repository.fetchSearchResultsByQuery("valid")

        actual.subscribe(listPageObserver)

        listPageObserver.assertNoErrors()
        assertEquals("actual did not equal mock observable", listPageObservable, actual)
    }


    @Test
    fun `GIVEN query is invalid THEN fetchSearchResultsByQuery should return empty search results`() {
        every { helper.checkQueryIsValid(any()) }.returns(false)
        val actual = repository.fetchSearchResultsByQuery("invalid")

        actual.subscribe(listPageObserver)

        listPageObserver.assertComplete()
        listPageObserver.assertNoErrors()
        listPageObserver.assertSubscribed()
        listPageObserver.assertValue { page ->
            page.searchResults.isEmpty()
        }
        assertNotEquals("actual did equal mock observable", listPageObservable, actual)
    }

    private fun setupPagesResponses() {
        every { listService.getProductListPageWithSearchResultsByQuery(any()) }.returns(listPageObservable)
    }

    private fun setupSubscribers() {
        every { listPageObservable.subscribe(listPageObserver) }.returns(Unit)
    }
}