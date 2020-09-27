package com.n26zirkler.btcpricechart.framework.web_pricechart_datasource

import com.n26zirkler.core.data.PriceChartDataSource
import com.n26zirkler.core.domain.PriceChartData
import com.n26zirkler.core.domain.PriceChartTimeSpan
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Implementation of `PriceChartDataSource` which requests an actual web price chart API.
 */
class WebPriceChartDataSource @Inject constructor(
    val marketPriceRetrofitService: MarketPriceRetrofitService
) : PriceChartDataSource {
    override fun getChartPrice(timespan: PriceChartTimeSpan): Single<PriceChartData> {
        return marketPriceRetrofitService
            .getMarketPrice(timespan = timespan.value)
            .subscribeOn(Schedulers.io())
            .map { marketPriceResponse ->
                PriceChartData(
                    dates = marketPriceResponse.values.map { it.x },
                    values = marketPriceResponse.values.map { it.y }
                )
            }
    }
}
