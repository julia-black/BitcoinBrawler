package com.singlelab.bitcoinbrawler.ui.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.singlelab.bitcoinbrawler.model.Product
import com.singlelab.bitcoinbrawler.model.ProductType

class StoreViewModel : ViewModel() {

    var productsLiveData = MutableLiveData<List<Product>>()

    var userDollars = 0f

    private var userProducts: List<Product>? = null

    private val drillsList
        get() = Product.values().filter { it.type == ProductType.DRILL }

    private val pepesList
        get() = Product.values().filter { it.type == ProductType.PEPE }

    private val othersList
        get() = Product.values().filter { it.type == ProductType.OTHER }.toMutableList()

    fun getUserProducts() = userProducts

    fun setUserProducts(products: List<Product>) {
        userProducts = products.toList()
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
            if (userPepe == Product.PEPE_5) {
                result.add(Product.PEPE_5)
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
            if (userDrill == Product.DRILL_3) {
                result.add(Product.DRILL_3)
            }
        }
        result.addAll(othersList)

        return result
    }
}