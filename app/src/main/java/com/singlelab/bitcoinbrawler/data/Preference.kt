package com.singlelab.bitcoinbrawler.data

import android.content.SharedPreferences
import android.util.Log
import com.singlelab.bitcoinbrawler.model.Const
import com.singlelab.bitcoinbrawler.model.Product
import com.singlelab.bitcoinbrawler.model.User

class Preference(private val sharedPreferences: SharedPreferences) {

    fun updateUserData(it: User) {
        sharedPreferences
            .edit()
            .putInt(Const.PREF_USER_BTC, it.amountBtc)
            .putFloat(Const.PREF_USER_DOLLARS, it.amountDollar)
            .putInt(Const.PREF_VELOCITY, it.velocity)
            .putString(Const.PREF_PRODUCT_IDS, it.productIds)
            .apply()
    }

    fun getUserData(): User {
        return User(
            sharedPreferences.getInt(Const.PREF_USER_BTC, 0),
            sharedPreferences.getFloat(Const.PREF_USER_DOLLARS, 0f),
            sharedPreferences.getInt(Const.PREF_VELOCITY, 1),
            getProducts(
                sharedPreferences.getString(Const.PREF_PRODUCT_IDS, "PEPE_1,DRILL_1") ?: ""
            ).toMutableList()
        )
    }

    fun savePrices(prices: List<Float>) {
        sharedPreferences
            .edit()
            .putString(Const.PREF_PRICES, prices.joinToString(","))
            .apply()
    }

    fun getPrices(): List<Float> =
        sharedPreferences.getString(Const.PREF_PRICES, "100.0, 100.03, 100.06, 100.04, 100.05")!!
            .split(",").map {
            it.toFloat()
        }

    private fun getProducts(productIds: String): List<Product> {
        return productIds.split(",").mapNotNull {
            Log.d("123LOG", "product = $it")
            return@mapNotNull if (it.isEmpty()) {
                null
            } else {
                Product.valueOf(it)
            }
        }
    }
}