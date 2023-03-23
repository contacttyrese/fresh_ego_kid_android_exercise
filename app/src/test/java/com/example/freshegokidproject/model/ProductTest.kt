package com.example.freshegokidproject.model

import android.os.Parcel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class ProductTest {
    @Test
    fun `can be instantiated`() {
        val p = Product("a", "a", "a", "a")
        assertNotNull("product is null", p)
    }

    @Test
    fun `key, title, price and description are as expected`() {
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

        val p = Product(mockParcel)
        assertEquals(expectedValue, p.key)
    }

//    @Test
//    fun `parcel can be written to as expected`() {
//        val expectedValue = "test_value"
//        val product = Product("test_key", "test_title", "test_price", "test_description")
//        val prod = mockk<Product>()
//        val mockParcel = spyk<Parcel>()
//
//        every { mockParcel.readString() }.returns("test_value")
//        every { mockParcel.writeString(anyCharVararg().toString()) }.answers { product.writeToParcel(mockParcel) }
//
//        product.writeToParcel(mockParcel, 0)
//        assertEquals(expectedValue, product.key)
//    }
}