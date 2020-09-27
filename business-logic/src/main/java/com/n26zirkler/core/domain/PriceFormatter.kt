package com.n26zirkler.core.domain

import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton

/**
 * PriceFormatter is responsible for formatting a given price to a nicer, easier to read
 * price string. For simplicity, this class does not support multiple locales.
 */
@Singleton
class PriceFormatter @Inject constructor() {
    private val decimalFormat = DecimalFormat("#,###.00 USD")
    fun format(price: Float): String = decimalFormat.format(price)
}
