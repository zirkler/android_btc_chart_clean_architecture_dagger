package com.n26zirkler.btcpricechart.di

import com.n26zirkler.core.data.FakePriceDataSource
import com.n26zirkler.core.data.PriceChartDataSource
import dagger.Module
import dagger.Provides

@Module
class TestChartPriceDataSourceModule {
    @Provides
    fun providePriceChartDataSource(): PriceChartDataSource = FakePriceDataSource()
}
