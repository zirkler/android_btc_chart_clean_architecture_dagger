package com.n26zirkler.btcpricechart.presentation.pricechart

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.n26zirkler.btcpricechart.R
import com.n26zirkler.btcpricechart.framework.BtcPriceChartApplication
import com.n26zirkler.btcpricechart.presentation.ViewModelFactory
import com.n26zirkler.core.domain.Fail
import com.n26zirkler.core.domain.Loading
import com.n26zirkler.core.domain.PriceChartData
import com.n26zirkler.core.domain.PriceChartTimeSpan
import com.n26zirkler.core.domain.Success
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_price_chart.lblDate
import kotlinx.android.synthetic.main.fragment_price_chart.lblValue
import kotlinx.android.synthetic.main.fragment_price_chart.lineChart
import kotlinx.android.synthetic.main.fragment_price_chart.llErrorContainer
import kotlinx.android.synthetic.main.fragment_price_chart.loadingProgressBar
import kotlinx.android.synthetic.main.fragment_price_chart.view.radioGroup

/**
 * The PriceChartFragment is the primary UI component of the app.
 * It displays the price chart and allows the user to drag his finger on the chart to
 * see the exact price at a given point in history in the price label.
 */
class PriceChartFragment : Fragment() {

    private lateinit var viewModel: PriceChartViewModel
    @Inject
    lateinit var viewModeFactory: ViewModelFactory<PriceChartViewModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_price_chart, container, false)
        view.radioGroup.setOnCheckedChangeListener { _, clickedRadioButtonId ->
            when (clickedRadioButtonId) {
                R.id.radio_all -> {
                    viewModel.loadPriceChartData(timespan = PriceChartTimeSpan.ALL)
                }
                R.id.radio_1_year -> {
                    viewModel.loadPriceChartData(timespan = PriceChartTimeSpan.ONE_YEAR)
                }
                R.id.radio_6_months -> {
                    viewModel.loadPriceChartData(timespan = PriceChartTimeSpan.SIX_MONTHS)
                }
                R.id.radio_five_weeks -> {
                    viewModel.loadPriceChartData(timespan = PriceChartTimeSpan.FIVE_WEEKS)
                }
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as BtcPriceChartApplication).appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModeFactory).get(PriceChartViewModel::class.java)
        viewModel.loadPriceChartData(timespan = PriceChartTimeSpan.ONE_YEAR)

        // Subscribe to the ViewModel state.
        viewModel.priceChartData.observe(this.viewLifecycleOwner, Observer { priceChartData ->
            when (priceChartData) {
                is Success -> {
                    lineChart.visibility = View.VISIBLE
                    setLineChartData(priceChartData = priceChartData())
                    loadingProgressBar.visibility = View.INVISIBLE
                    llErrorContainer.visibility = View.INVISIBLE
                }
                is Loading -> {
                    lineChart.visibility = View.INVISIBLE
                    loadingProgressBar.visibility = View.VISIBLE
                    llErrorContainer.visibility = View.INVISIBLE
                }
                is Fail -> {
                    lineChart.visibility = View.INVISIBLE
                    loadingProgressBar.visibility = View.INVISIBLE
                    llErrorContainer.visibility = View.VISIBLE
                }
            }
        })

        viewModel.currentlySelectedPriceString.observe(this.viewLifecycleOwner, {
            lblValue.text = it
        })
        viewModel.currentlySelectedDateString.observe(this.viewLifecycleOwner, {
            lblDate.text = it
        })
        setupChart()
    }

    /**
     * Updates the line chart with the provided PriceChartData.
     */
    private fun setLineChartData(priceChartData: PriceChartData) {
        val entries = priceChartData.dates.mapIndexed { i, time ->
            Entry(time.toFloat(), priceChartData.values[i])
        }

        val marketPriceDataSet = LineDataSet(entries, "Market Price").apply {
            setDrawCircles(false)
            color = Color.parseColor("#74E0A5")
            setDrawValues(false)
            setDrawHorizontalHighlightIndicator(false)
            highLightColor = Color.LTGRAY
        }

        lineChart.data = LineData(marketPriceDataSet)
        lineChart.invalidate()
    }

    private fun setupChart() {
        lineChart.axisLeft.apply {
            setDrawTopYLabelEntry(false)
            setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
            setDrawGridLines(false)
            gridColor = Color.LTGRAY
            textColor = Color.LTGRAY
            setDrawGridLinesBehindData(false)
        }

        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM_INSIDE
            granularity = 1f
            labelRotationAngle = 0f
            setDrawGridLines(false)
            gridColor = Color.LTGRAY
            textColor = Color.LTGRAY
            setAvoidFirstLastClipping(true)
            setDrawGridLinesBehindData(false)
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val sdf = SimpleDateFormat("MMM yy", Locale.US)
                    return sdf.format(Date(value.toLong() * 1000))
                }
            }
        }

        lineChart.apply {
            lineChart.axisRight.isEnabled = false
            lineChart.setDrawGridBackground(false)
            lineChart.setViewPortOffsets(0f, 0f, 0f, 0f)
            lineChart.description.isEnabled = false
            lineChart.legend.isEnabled = false
            lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(selectedEntry: Entry?, h: Highlight?) {
                    // Forward selection on the chart to the ViewModel,
                    // so it can update the selected price and date values.
                    selectedEntry?.let {
                        viewModel.setSelectedEntry(price = it.y, datetime = it.x.toLong())
                    }
                }

                override fun onNothingSelected() { }
            })
        }
    }
}
