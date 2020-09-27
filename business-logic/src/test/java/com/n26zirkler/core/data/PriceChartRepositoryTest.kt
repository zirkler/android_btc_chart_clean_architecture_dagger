package com.n26zirkler.core.data

import com.n26zirkler.core.domain.PriceChartData
import com.n26zirkler.core.domain.PriceChartTimeSpan
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock

class PriceChartRepositoryTest {

    @Test
    fun `should pass through data from data source`() {
        val mockDataSource = mock(PriceChartDataSource::class.java)
        val mockReturnValue = Single.just(PriceChartData(
            dates = listOf(1L,2L,3L),
            values = listOf(1f,2f,3f)
        ))
        `when`(mockDataSource.getChartPrice(PriceChartTimeSpan.ONE_YEAR)).thenReturn(mockReturnValue)
        val chartData = mockDataSource.getChartPrice(PriceChartTimeSpan.ONE_YEAR).blockingGet()
        assertEquals(chartData.values.size, 3)
        assertEquals(chartData.dates.size, 3)
    }
}
