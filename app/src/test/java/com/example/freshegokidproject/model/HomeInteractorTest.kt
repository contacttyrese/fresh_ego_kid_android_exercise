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

class HomeInteractorTest {
    @get:Rule
    val rule = MockKRule(this)
    @get:Rule
    val schedulerRule = RxSchedulerRule()
    @get:Rule
    val logRule = LogRule()

    @MockK
    private lateinit var repository: HomeRepository
    @MockK
    private lateinit var mockHomePageObservable: Observable<HomePage>
    @MockK
    private lateinit var helper: InteractorHelper

    private lateinit var interactor: HomeInteractor

    private val homePageObserver = TestObserver<HomePage>()

    @Before
    fun setUp() {
        interactor = HomeInteractor(repository, helper)
        setupHomePageObservable()
    }

    @Test
    fun `GIVEN getPageObservableWithHomeBannerAndHomeProducts THEN returns mock observable`() {
        val actual = interactor.getPageObservableWithHomeBannerAndHomeProducts()
        actual.subscribe(homePageObserver)

        homePageObserver.assertNoErrors()
        assertEquals("did not equal mock observable", mockHomePageObservable, actual)
    }

    private fun setupHomePageObservable() {
        every { mockHomePageObservable.subscribeOn(any()) }.returns(mockHomePageObservable)
        every { mockHomePageObservable.doOnNext(any()) }.returns(mockHomePageObservable)
        every { mockHomePageObservable.observeOn(any()) }.returns(mockHomePageObservable)
        every { mockHomePageObservable.subscribe(homePageObserver) }.returns(Unit)
        every { repository.fetchHomeBannerAndProducts() }.returns(mockHomePageObservable)
    }
}