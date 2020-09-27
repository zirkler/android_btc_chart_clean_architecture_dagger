package com.n26zirkler.core.domain

/**
 * Represents a subset of available timespans which can be used to query the API.
 */
enum class PriceChartTimeSpan(val value: String) {
    ALL("all"),
    ONE_YEAR("1years"),
    SIX_MONTHS("6months"),
    FIVE_WEEKS("5weeks")
}
