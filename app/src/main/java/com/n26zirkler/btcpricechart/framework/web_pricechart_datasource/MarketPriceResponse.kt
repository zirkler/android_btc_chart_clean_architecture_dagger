package com.n26zirkler.btcpricechart.framework.web_pricechart_datasource

/**
 * MarketPriceResponse hold the API response from the price chart API.
 */
data class MarketPriceResponse(
    val status: String,
    val values: List<MarketPriceValue>
) {
    data class MarketPriceValue(val x: Long, val y: Float)
}
