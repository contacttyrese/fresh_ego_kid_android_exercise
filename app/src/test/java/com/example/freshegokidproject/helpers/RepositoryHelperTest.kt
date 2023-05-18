package com.example.freshegokidproject.helpers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.freshegokidproject.rules.LogRule
import io.mockk.every
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class RepositoryHelperTest {
    @get:Rule
    val logRule = LogRule()

    private val helper = RepositoryHelper()

    @Test
    fun `GIVEN query is one letter THEN checkQueryIsValid should return true`() {
        val actual = helper.checkQueryIsValid("a")

        assertTrue("did not return true", actual)
    }

    @Test
    fun `GIVEN query is one word THEN checkQueryIsValid should return true`() {
        val actual = helper.checkQueryIsValid("hat")

        assertTrue("did not return true", actual)
    }

    @Test
    fun `GIVEN query is more than one word THEN checkQueryIsValid should return true`() {
        val actual = helper.checkQueryIsValid("blue hat")

        assertTrue("did not return true", actual)
    }

    @Test
    fun `GIVEN query is a number THEN checkQueryIsValid should return false`() {
        val actual = helper.checkQueryIsValid("404")

        assertFalse("did not return false", actual)
    }

    @Test
    fun `GIVEN query is empty THEN checkQueryIsValid should return false`() {
        val actual = helper.checkQueryIsValid("")

        assertFalse("did not return false", actual)
    }

    @Test
    fun `GIVEN query is blank THEN checkQueryIsValid should return false`() {
        val actual = helper.checkQueryIsValid("   ")

        assertFalse("did not return false", actual)
    }
}