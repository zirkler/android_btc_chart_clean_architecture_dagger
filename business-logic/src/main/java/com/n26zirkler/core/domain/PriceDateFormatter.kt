package com.n26zirkler.core.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * PriceDateFormatter is responsible for formatting a given timestamp to a
 * human readable date string. For simplicity, this class does not support multiple locales.
 */
@Singleton
class PriceDateFormatter @Inject constructor() {
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    fun format(date: Date): String = simpleDateFormat.format(date)
}
