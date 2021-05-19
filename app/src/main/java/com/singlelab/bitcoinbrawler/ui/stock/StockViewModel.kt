package com.singlelab.bitcoinbrawler.ui.stock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StockViewModel : ViewModel() {

    val prices = MutableLiveData<List<Double>>().apply {
        value = listOf(10.0, 11.2, 12.0, 13.0, 11.4)
    }
}