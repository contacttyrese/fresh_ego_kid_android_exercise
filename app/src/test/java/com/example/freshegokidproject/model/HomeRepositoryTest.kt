package com.example.freshegokidproject.model

import com.example.freshegokidproject.network.HomeService
import com.example.freshegokidproject.rules.LogRule
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeRepositoryTest {
    @get:Rule
    val logRule = LogRule()

    private val homeService = mockk<HomeService>()
    private val homePageObservable = mockk<Observable<HomePage>>()
    private val homePageObserver = TestObserver<HomePage>()

    private val repository = HomeRepository(homeService)

    @Before
    fun setUp() {
        setupPagesResponses()
        setupSubscribers()
    }

    @Test
    fun `GIVEN fetchHomeBannerAndProducts THEN should return mock observable`() {
        val actual = repository.fetchHomeBannerAndProducts()

        actual.subscribe(homePageObserver)

        homePageObserver.assertNoErrors()
        assertEquals("actual did not mock observable", homePageObservable, actual)
    }

    private fun setupPagesResponses() {
        every { homeService.getHomepage() }.returns(homePageObservable)
    }

    private fun setupSubscribers() {
        every { homePageObservable.subscribe(homePageObserver) }.returns(Unit)
    }
}