package com.n26zirkler.btcpricechart.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.n26zirkler.btcpricechart.R
import kotlinx.android.synthetic.main.fragment_price_chart.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
