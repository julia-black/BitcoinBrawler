package com.singlelab.bitcoinbrawler.ui.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.singlelab.bitcoinbrawler.model.Product
import com.singlelab.bitcoinbrawler.model.ProductType

class StoreViewModel : ViewModel() {

    val products = MutableLiveData<List<Product>>().apply {
        value = listOf(
            Product(0, "Pepe+", 100, ProductType.PEPE, 10),
            Product(1, "Drill+", 50, ProductType.DRILL, 15)
        )
    }
}