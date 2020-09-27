package com.n26zirkler.btcpricechart.di

import com.n26zirkler.btcpricechart.framework.web_pricechart_datasource.MarketPriceRetrofitService
import com.n26zirkler.btcpricechart.framework.web_pricechart_datasource.WebPriceChartDataSource
import com.n26zirkler.core.data.PriceChartDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class PriceChartDataSourceModule {
    @Provides
    fun providePriceChartDataSource(retrofitService: MarketPriceRetrofitService): PriceChartDataSource =
        WebPriceChartDataSource(marketPriceRetrofitService = retrofitService)

    @Provides
    fun providePriceChartRetrofitService(): MarketPriceRetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://api.blockchain.info")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarketPriceRetrofitService::class.java)
    }
}
