package com.n26zirkler.core.data

import com.n26zirkler.core.domain.PriceChartTimeSpan
import org.junit.Assert.assertEquals
import org.junit.Test

class PriceChartRepositoryTest {

    @Test
    fun `should pass through data from data source`() {
        val repository = PriceChartRepository(dataSource = FakePriceDataSource())
        val chartData = repository.getChartPrice(PriceChartTimeSpan.ONE_YEAR).blockingGet()
        assertEquals(chartData.values.size, 365)
    }
}
