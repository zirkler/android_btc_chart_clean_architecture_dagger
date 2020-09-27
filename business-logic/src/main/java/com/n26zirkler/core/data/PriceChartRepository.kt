package com.n26zirkler.core.data

import com.n26zirkler.core.domain.PriceChartData
import com.n26zirkler.core.domain.PriceChartTimeSpan
import io.reactivex.Single
import javax.inject.Inject

/**
 * The PriceChartRepository allows access to the data fetched by the PriceChartDataSource.
 * We are using the Repository pattern to abstract away the concrete implementation of
 * data access by the data source.
 */
class PriceChartRepository @Inject constructor(private val dataSource: PriceChartDataSource) {
    fun getChartPrice(timespan: PriceChartTimeSpan): Single<PriceChartData> {
        return dataSource.getChartPrice(timespan = timespan)
    }
}
