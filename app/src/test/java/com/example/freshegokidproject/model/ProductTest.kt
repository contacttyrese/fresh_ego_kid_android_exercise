package com.example.freshegokidproject.model

import android.os.Parcel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class ProductTest {
    @Test
    fun `object can be instantiated`() {
        val product = Product("a", "a", "a", "a")
        assertNotNull("product is null", product)
    }

    @Test
    fun `key, title, price and description are as expected when passing to constructor`() {
        val expectedKey = "test_key"
        val expectedTitle = "test_title"
        val expectedPrice = "test_price"
        val expectedDescription = "test_description"

        val p = Product("test_key", "test_title", "test_price", "test_description")
        assertEquals(expectedKey, p.key)
        assertEquals(expectedTitle, p.title)
        assertEquals(expectedPrice, p.price)
        assertEquals(expectedDescription, p.description)
    }

    @Test
    fun `parcel value can be retireved as expected`() {
        val expectedValue = "test_value"
        val mockParcel = mockk<Parcel>()

        every { mockParcel.readString() }.returns("test_value")

        val product = Product(mockParcel)
        assertEquals(expectedValue, product.key)
    }

    @Test
    fun `parcel is written to when write to parcel is called`() {
        val product = Product("test_key", "test_title", "test_price", "test_description")
        val mockParcel = mockk<Parcel>()

        every { mockParcel.writeString(any()) } returns Unit
        product.writeToParcel(mockParcel, 0)

        verify { mockParcel.writeString(any()) }
    }
}