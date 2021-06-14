package com.singlelab.bitcoinbrawler.ui.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.singlelab.bitcoinbrawler.model.Const
import com.singlelab.bitcoinbrawler.model.Product
import com.singlelab.bitcoinbrawler.model.ProductType

class StoreViewModel : ViewModel() {

    private var userProducts: List<Product>? = null

    var productsLiveData = MutableLiveData<List<Product>>()

    fun setUserProducts(products: List<Product>) {
        userProducts = products
        productsLiveData.value = getProducts()
    }

    private fun getProducts(): List<Product> {
        val result = mutableListOf<Product>()
        val userPepe = userProducts?.findLast {
            it.type == ProductType.PEPE
        }
        if (userPepe == null) {
            result.add(pepesList[0])
        } else {
            pepesList.find {
                it.id == userPepe.id + 1
            }?.let {
                result.add(it)
            }
        }

        val userDrill = userProducts?.findLast {
            it.type == ProductType.DRILL
        }
        if (userDrill == null) {
            result.add(drillsList[0])
        } else {
            drillsList.find {
                it.id == userDrill.id + 1
            }?.let {
                result.add(it)
            }
        }
        return result
    }

    companion object {
        private val pepesList = listOf(
            Product(Const.PEPE_ID_1, "Pepe 1", 100, ProductType.PEPE, "Это пепе 1", 10),
            Product(Const.PEPE_ID_2, "Pepe 2", 200, ProductType.PEPE, "Это пепе 2", 10),
            Product(Const.PEPE_ID_3, "Pepe 3", 300, ProductType.PEPE, "Это пепе 3", 10),
            Product(Const.PEPE_ID_4, "Pepe 4", 400, ProductType.PEPE, "Это пепе 4", 10),
            Product(Const.PEPE_ID_5, "Pepe 5", 500, ProductType.PEPE, "Это пепе 5", 10),
        )

        private val drillsList = listOf(
            Product(Const.DRILL_ID_1, "Drill 1", 50, ProductType.DRILL, "Это дрель 1", 15),
            Product(Const.DRILL_ID_2, "Drill 2", 300, ProductType.DRILL, "Это дрель 2", 15),
            Product(Const.DRILL_ID_3, "Drill 3", 1000, ProductType.DRILL, "Это дрель 3", 15),
        )
    }
}