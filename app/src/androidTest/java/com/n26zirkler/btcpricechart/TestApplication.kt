package com.n26zirkler.btcpricechart

import com.n26zirkler.btcpricechart.di.AppComponent
import com.n26zirkler.btcpricechart.di.DaggerTestAppComponent
import com.n26zirkler.btcpricechart.presentation.BtcPriceChartApplication

/**
 * TestApplication will override MyApplication in android tests
 */
class TestApplication : BtcPriceChartApplication() {
    override fun initializeAppComponent(): AppComponent {
        return DaggerTestAppComponent.factory().create(this)
    }
}
