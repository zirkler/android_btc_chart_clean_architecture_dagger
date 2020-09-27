package com.n26zirkler.btcpricechart.presentation.pricechart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.n26zirkler.core.domain.Async
import com.n26zirkler.core.domain.Fail
import com.n26zirkler.core.domain.Loading
import com.n26zirkler.core.domain.PriceChartData
import com.n26zirkler.core.domain.PriceChartTimeSpan
import com.n26zirkler.core.domain.PriceDateFormatter
import com.n26zirkler.core.domain.PriceFormatter
import com.n26zirkler.core.domain.Success
import com.n26zirkler.core.interactor.GetPriceChartInteractor
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.Date
import javax.inject.Inject

class PriceChartViewModel @Inject constructor(
    application: Application,
    val getPriceChartInteractor: GetPriceChartInteractor,
    val priceDateFormatter: PriceDateFormatter,
    val priceFormatter: PriceFormatter
) : AndroidViewModel(application) {

    // Exposed state
    val currentlySelectedPriceString: MutableLiveData<String> = MutableLiveData()
    val currentlySelectedDateString: MutableLiveData<String> = MutableLiveData()
    val priceChartData: MutableLiveData<Async<PriceChartData>> = MutableLiveData()

    private var loadPriceDataDisposable: Disposable? = null

    /**
     * Loads the price chart data for a given timespan.
     */
    fun loadPriceChartData(timespan: PriceChartTimeSpan) {
        priceChartData.value = Loading()
        loadPriceDataDisposable?.dispose()
        loadPriceDataDisposable = getPriceChartInteractor(timespan = timespan)
            .subscribeBy(
                onSuccess = { receivedPriceChartData ->
                    priceChartData.postValue(Success(receivedPriceChartData))

                    // Set selected price to most recent price data.
                    receivedPriceChartData.values.lastOrNull()?.let { lastValue ->
                        receivedPriceChartData.dates.lastOrNull()?.let { lastDate ->
                            setSelectedEntry(
                                price = lastValue,
                                datetime = lastDate
                            )
                        }
                    }
                },
                onError = {
                    priceChartData.postValue(Fail(it))
                })
    }

    /**
     * Updates the selected price and date state depending on the selected chart entry.
     */
    fun setSelectedEntry(price: Float, datetime: Long) {
        currentlySelectedPriceString.postValue(
            priceFormatter.format(price)
        )
        val selectedDate = Date(datetime * 1000)
        currentlySelectedDateString.postValue(priceDateFormatter.format(date = selectedDate))
    }
}
