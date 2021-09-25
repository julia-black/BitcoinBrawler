package com.singlelab.bitcoinbrawler.ui.stock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.singlelab.bitcoinbrawler.model.User

class StockViewModel : ViewModel() {

    var userLiveData = MutableLiveData<User?>()

    fun buyBtc(amount: Float?, user: User?, price: Float?) {
        if (amount != null && price != null && user != null && amount > 0) {
            userLiveData.value = user.buyBtc(amount, price)
        }
    }

    fun sellBtc(amount: Float?, user: User?, price: Float?) {
        if (amount != null && price != null && user != null && amount > 0) {
            userLiveData.value = user.sellBtc(amount, price)
        }
    }
}