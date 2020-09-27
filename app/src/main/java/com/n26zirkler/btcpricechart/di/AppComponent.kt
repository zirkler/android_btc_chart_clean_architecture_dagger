package com.n26zirkler.btcpricechart.di

import android.app.Application
import com.n26zirkler.btcpricechart.presentation.pricechart.PriceChartFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PriceChartDataSourceModule::class])
interface AppComponent {

    // A factory to create instances of the AppComponent.
    @Component.Factory
    interface AppComponentFactory {
        // The application object passed in will be available in the dependency graph.
        fun create(@BindsInstance application: Application): AppComponent
    }

    // Tell Dagger that the PriceChartFragment request injection.
    fun inject(fragment: PriceChartFragment)
}
