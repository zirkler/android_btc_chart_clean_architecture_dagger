package com.n26zirkler.btcpricechart.framework.web_pricechart_datasource

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The retrofit service for communicating with the bitcoin market price API.
 */
interface MarketPriceRetrofitService {

    /**
     * Requests the market price api for price data respecting the provided timespan.
     */
    @GET("charts/market-price")
    fun getMarketPrice(
        @Query("timespan") timespan: String
    ): Single<MarketPriceResponse>
}
