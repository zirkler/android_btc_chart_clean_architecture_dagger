package com.n26zirkler.btcpricechart.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// Replacement for ApPComponent in android tests
@Singleton
@Component(modules = [TestChartPriceDataSourceModule::class])
interface TestAppComponent : AppComponent {

    // A factory to create instances of the TestAppComponent.
    @Component.Factory
    interface AppComponentFactory {
        // The application object passed in will be available in the dependency graph.
        fun create(@BindsInstance application: Application): AppComponent
    }
}
