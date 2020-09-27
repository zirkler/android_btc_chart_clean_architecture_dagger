package com.n26zirkler.btcpricechart.pricechart

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.n26zirkler.btcpricechart.FakePriceDataSource
import com.n26zirkler.btcpricechart.LiveDataTestUtil
import com.n26zirkler.btcpricechart.presentation.pricechart.PriceChartViewModel
import com.n26zirkler.core.data.PriceChartRepository
import com.n26zirkler.core.domain.Fail
import com.n26zirkler.core.domain.PriceChartTimeSpan
import com.n26zirkler.core.domain.PriceDateFormatter
import com.n26zirkler.core.domain.PriceFormatter
import com.n26zirkler.core.domain.Success
import com.n26zirkler.core.interactor.GetPriceChartInteractor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class PriceChartViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PriceChartViewModel

    @Before
    fun setup() {
        val appMock = Mockito.mock(Application::class.java)
        val priceChartRepository = PriceChartRepository(FakePriceDataSource())
        viewModel = PriceChartViewModel(
            application = appMock,
            getPriceChartInteractor = GetPriceChartInteractor(priceChartRepository = priceChartRepository),
            priceDateFormatter = PriceDateFormatter(),
            priceFormatter = PriceFormatter()
        )
    }

    @Test
    fun `loadPriceChartData() sets success state correctly`() {
        viewModel.loadPriceChartData(timespan = PriceChartTimeSpan.ONE_YEAR)
        val priceChartData = LiveDataTestUtil.getValue(viewModel.priceChartData)
        assert(priceChartData is Success)
        assertEquals(365, priceChartData()!!.values.size)

        assertEquals(
            "01/01/2011",
            LiveDataTestUtil.getValue(viewModel.currentlySelectedDateString)
        )

        assertEquals(
            "66.612,50 USD",
            LiveDataTestUtil.getValue(viewModel.currentlySelectedPriceString)
        )
    }

    @Test
    fun `loadPriceChartData() sets failure state correctly`() {
        viewModel.loadPriceChartData(timespan = PriceChartTimeSpan.FIVE_WEEKS)
        val priceChartData = LiveDataTestUtil.getValue(viewModel.priceChartData)
        assert(priceChartData is Fail)
    }

    @Test
    fun `setSelectedEntry() sets state correctly`() {
        viewModel.setSelectedEntry(
            price = 11081.43F,
            datetime = 1601213976L
        )

        assertEquals(
            "27/09/2020",
            LiveDataTestUtil.getValue(viewModel.currentlySelectedDateString)
        )

        assertEquals(
            "11.081,43 USD",
            LiveDataTestUtil.getValue(viewModel.currentlySelectedPriceString)
        )
    }
}
