package com.n26zirkler.core.domain

import java.util.Date
import org.junit.Assert
import org.junit.Test

class PriceDateFormatterTest {
    val formatter = PriceDateFormatter()

    @Test
    fun `should format dates correctly`() {
        Assert.assertEquals("27/09/2020", formatter.format(Date(1601213976000L)))
        Assert.assertEquals("28/07/2017", formatter.format(Date(1501213976000L)))
    }
}
