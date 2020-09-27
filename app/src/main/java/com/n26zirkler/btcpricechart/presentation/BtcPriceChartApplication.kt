package com.n26zirkler.btcpricechart.presentation

import android.app.Application
import com.n26zirkler.btcpricechart.di.AppComponent
import com.n26zirkler.btcpricechart.di.DaggerAppComponent

open class BtcPriceChartApplication : Application() {
    val appComponent: AppComponent by lazy {
        initializeAppComponent()
    }

    open fun initializeAppComponent(): AppComponent {
        return DaggerAppComponent.factory().create(application = this)
    }
}
