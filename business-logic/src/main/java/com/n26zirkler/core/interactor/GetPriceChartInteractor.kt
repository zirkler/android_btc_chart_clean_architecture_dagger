package com.n26zirkler.core.interactor

import com.n26zirkler.core.data.PriceChartRepository
import com.n26zirkler.core.domain.PriceChartTimeSpan
import javax.inject.Inject

/**
 * The GetChartPriceInteractor represents the use case of getting price chart data.
 * This Interactor is the primary interface from the outside to the inside domain layers.
 */
class GetPriceChartInteractor @Inject constructor(
    private val priceChartRepository: PriceChartRepository
) {
    operator fun invoke(timespan: PriceChartTimeSpan) =
            priceChartRepository.getChartPrice(timespan = timespan)
}
