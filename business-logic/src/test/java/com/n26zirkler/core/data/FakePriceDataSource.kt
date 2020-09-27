package com.n26zirkler.core.data

import com.n26zirkler.core.domain.PriceChartData
import com.n26zirkler.core.domain.PriceChartTimeSpan
import io.reactivex.Single

class FakePriceDataSource : PriceChartDataSource {
    override fun getChartPrice(timespan: PriceChartTimeSpan): Single<PriceChartData> {
        val baseTimestamp = 1262304000
        return when (timespan) {
            PriceChartTimeSpan.ALL -> {
                Single.just(
                    PriceChartData(
                        dates = (1..2000).map { baseTimestamp + (it * 60 * 60 * 24).toLong() },
                        values = (1..2000).map { (it * it).toFloat() }
                    )
                )
            }
            PriceChartTimeSpan.ONE_YEAR -> {
                Single.just(
                    PriceChartData(
                        dates = (1..365).map { baseTimestamp + (it * 60 * 60 * 24).toLong() },
                        values = (1..365).map { (it * it) * 0.5f }
                    )
                )
            }
            PriceChartTimeSpan.SIX_MONTHS -> {
                Single.just(
                    PriceChartData(
                        dates = (1..182).map { baseTimestamp + (it * 60 * 60 * 24).toLong() },
                        values = (1..182).map { (it * it).toFloat() * 0.05f }
                    )
                )
            }
            PriceChartTimeSpan.FIVE_WEEKS -> Single.fromCallable { throw Error("FakePriceDataSource Error") }
        }
    }
}
