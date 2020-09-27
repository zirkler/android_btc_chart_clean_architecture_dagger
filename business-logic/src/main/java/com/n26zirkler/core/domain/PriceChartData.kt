package com.n26zirkler.core.domain

/**
 * PriceChartData holds all the data vor displaying a price chart.
 */
data class PriceChartData(val dates: List<Long>, val values: List<Float>)
