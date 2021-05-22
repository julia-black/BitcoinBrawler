package com.singlelab.bitcoinbrawler.ui.stock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.singlelab.bitcoinbrawler.model.User

class StockViewModel : ViewModel() {

    var userLiveData = MutableLiveData<User?>()

    fun buyBtc(amount: Int?, user: User?, price: Double?) {
        if (amount != null && price != null && user != null) {
            userLiveData.value = user.buyBtc(amount, price)
        }
    }

    fun sellBtc(amount: Int?, user: User?, price: Double?) {
        if (amount != null && price != null && user != null) {
            userLiveData.value = user.sellBtc(amount, price)
        }
    }
}