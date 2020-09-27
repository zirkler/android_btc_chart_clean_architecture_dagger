package com.n26zirkler.core.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class PriceFormatterTest {

    val formatter = PriceFormatter()

    @Test
    fun `should format prices correctly`() {
        assertEquals("99,00 USD", formatter.format(99f))
        assertEquals("999,99 USD", formatter.format(999.99f))
        assertEquals("9.999,99 USD", formatter.format(9999.99f))
    }
}
