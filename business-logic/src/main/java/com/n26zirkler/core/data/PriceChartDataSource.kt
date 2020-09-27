package com.n26zirkler.core.data

import com.n26zirkler.core.domain.PriceChartData
import com.n26zirkler.core.domain.PriceChartTimeSpan
import io.reactivex.Single

/**
 * Implementations of the PriceChartDataSource are responsible for the actual fetching/receiving
 * of chart data.
 */
interface PriceChartDataSource {
    fun getChartPrice(timespan: PriceChartTimeSpan): Single<PriceChartData>
}
